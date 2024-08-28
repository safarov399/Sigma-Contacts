package me.safarov399.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.save_location.SaveLocationFragment
import me.safarov399.common.custom_views.home.save_location.SaveLocationDropDownButton
import me.safarov399.common.dialogs.PermissionRequestDialog
import me.safarov399.core.navigation.NavigationManager
import me.safarov399.core.adapter.ContactAdapter
import me.safarov399.core.adapter.OnClickListener
import me.safarov399.core.base.AppBottomSheet
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.entity.ContactEntity
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_ADD
import me.safarov399.core.navigation.NavigationDestinationHandler.NAVIGATE_TO_DETAILS
import me.safarov399.home.databinding.FragmentHomeBinding
import me.safarov399.label.LabelFragment


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, HomeState, HomeEffect, HomeEvent>(), AppBottomSheet.AppBottomSheetListener {

    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
    )

    private var listener: AppBottomSheet.AppBottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        listener = this
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private var contactsAdapter: ContactAdapter? = null
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        for (permission in permissions) {
            if (!permission.value) {
                break
            }
        }

        if (hasContactsReadPermission()) {
            postEvent(
                HomeEvent.ReadContacts
            )
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) || !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                goToSettingsDialog()
            } else {
                activity?.finish()
            }
        }
    }

    override fun onStateUpdate(state: HomeState) {
        contactsAdapter?.submitList(state.contactEntity)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    private fun configureViews() {
        contactsAdapter = ContactAdapter()
        contactsAdapter?.setOnClickListener(
            object: OnClickListener {
                override fun onClick(position: Int, model: ContactEntity) {
                    val context = requireActivity() as NavigationManager
                    context.navigateToFullScreenActivity(NAVIGATE_TO_DETAILS, model.id)
                }
            }
        )
        binding.homeRecyclerView.adapter = contactsAdapter
        askContactsPermission()

        binding.homeFab.setOnClickListener {
            val context = requireActivity() as NavigationManager
            context.navigateToFullScreenActivity(NAVIGATE_TO_ADD)
        }

        binding.homeUtilityBar.findViewById<SaveLocationDropDownButton>(me.safarov399.common.R.id.utility_bar_drop_down).setOnClickListener {
            val bottomSheet = AppBottomSheet(listener!!) {
                SaveLocationFragment()
            }
            if (!bottomSheet.isAdded) {
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
                selectSaveLocationDropDown()

            }
        }

        binding.homeUtilityBar.findViewById<ImageView>(me.safarov399.common.R.id.utility_bar_label_iv).setOnClickListener {
            val bottomSheet = AppBottomSheet(listener!!) {
                LabelFragment()
            }
            if (!bottomSheet.isAdded) {
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        }
    }

    private fun selectSaveLocationDropDown() {
        binding.homeUtilityBar.findViewById<ImageView>(me.safarov399.common.R.id.save_location_drop_down_view_down).setImageDrawable(
            ResourcesCompat.getDrawable(resources, me.safarov399.common.R.drawable.drop_up, null)
        )
        binding.homeUtilityBar.findViewById<SaveLocationDropDownButton>(me.safarov399.common.R.id.utility_bar_drop_down).background = ResourcesCompat.getDrawable(resources, me.safarov399.common.R.drawable.savelocation_drop_down_button_background, null)
    }

    private fun unSelectSaveLocationDropDown() {
        binding.homeUtilityBar.findViewById<ImageView>(me.safarov399.common.R.id.save_location_drop_down_view_down).setImageDrawable(
            ResourcesCompat.getDrawable(resources, me.safarov399.common.R.drawable.drop_down, null)
        )
        binding.homeUtilityBar.findViewById<SaveLocationDropDownButton>(me.safarov399.common.R.id.utility_bar_drop_down).background = null


    }

    override fun onResume() {
        super.onResume()
        unSelectSaveLocationDropDown()
    }

    private fun askContactsPermissionDialog() {
        val askContactsDialog = PermissionRequestDialog(requireContext())
        askContactsDialog.setTitle("Permission Required")
        askContactsDialog.setDescription("Please provide permission to access contacts so that they can be displayed.")
        askContactsDialog.setOkButtonText("Give permission")
        askContactsDialog.setCancelButtonText("Exit the app")
        askContactsDialog.setOkButtonOnClickListener {
            askContactsDialog.dismiss()
            requestPermissionLauncher.launch(requiredPermissions)
        }
        askContactsDialog.setCancelButtonOnClickListener {
            activity?.finish()
        }
        askContactsDialog.show()
    }


    private fun goToSettingsDialog() {
        val dialog = PermissionRequestDialog(requireContext())
        dialog.setTitle("Permission not granted")
        dialog.setDescription("Please go to settings to provide the contacts permission.")
        dialog.setOkButtonText("Go to settings")
        dialog.setOkButtonOnClickListener {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + requireContext().packageName)
            )
            startActivity(intent)
            dialog.dismiss()
            activity?.finish()
        }
        dialog.setCancelButtonText("Exit the app")
        dialog.setCancelButtonOnClickListener {
            activity?.finish()
        }
        dialog.show()
    }


    private fun askContactsPermission() {
        if (hasContactsReadPermission()) {
            Log.e("askContactsPermission", "Has contacts permission")
            postEvent(
                HomeEvent.ReadContacts
            )
        } else {
            askContactsPermissionDialog()
        }
    }

    private fun hasContactsReadPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }


    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding = { inflater, viewGroup, value ->
        FragmentHomeBinding.inflate(inflater, viewGroup, value)
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun onBottomSheetClosedOrDismissed() {
        unSelectSaveLocationDropDown()
    }
}