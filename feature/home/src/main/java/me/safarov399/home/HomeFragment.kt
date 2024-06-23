package me.safarov399.home

import android.Manifest
import android.annotation.SuppressLint
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
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.bottom_sheet.SaveLocationFragment
import me.safarov399.common.custom_views.SaveLocationDropDownButton
import me.safarov399.common.dialogs.PermissionRequestDialog
import me.safarov399.core.adapter.ContactAdapter
import me.safarov399.core.base.AppBottomSheet
import me.safarov399.core.base.BaseFragment
import me.safarov399.home.databinding.FragmentHomeBinding


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel, HomeState, HomeEffect, HomeEvent>(), AppBottomSheet.AppBottomSheetListener {

        private var listener: AppBottomSheet.AppBottomSheetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listener = this
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private var contactsAdapter: ContactAdapter? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                postEvent(
                    HomeEvent.ReadContacts
                )
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
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
//        postEvent(
//            HomeEvent.InsertSaveLocation
//        )


    }

    private fun configureViews() {
        contactsAdapter = ContactAdapter()
        binding.homeRecyclerView.adapter = contactsAdapter
        askContactsPermission()

        binding.homeFab.setOnClickListener {

        }

        binding.homeUtilityBar.findViewById<SaveLocationDropDownButton>(me.safarov399.common.R.id.utility_bar_drop_down)
            .setOnClickListener {
                val bottomSheet = AppBottomSheet(listener!!) {
                    SaveLocationFragment()
                }
                if (!bottomSheet.isAdded) {
                    bottomSheet.show(parentFragmentManager, bottomSheet.tag)
                    selectSaveLocationDropDown()
                }
            }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("UseCompatLoadingForDrawables")
    fun selectSaveLocationDropDown() {
        binding.homeUtilityBar.findViewById<ImageView>(me.safarov399.common.R.id.save_location_drop_down_view_down)
            .setImageDrawable(
                resources.getDrawable(me.safarov399.common.R.drawable.drop_up)
            )
        binding.homeUtilityBar.setBackgroundDrawable(resources.getDrawable(me.safarov399.common.R.drawable.savelocation_drop_down_button_background))
    }

    @Suppress("DEPRECATION")
    @SuppressLint("UseCompatLoadingForDrawables")
    fun unSelectSaveLocationDropDown() {
        binding.homeUtilityBar.findViewById<ImageView>(me.safarov399.common.R.id.save_location_drop_down_view_down)
            .setImageDrawable(
                resources.getDrawable(me.safarov399.common.R.drawable.drop_down)
            )
        binding.homeUtilityBar.setBackgroundDrawable(null)


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
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
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
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + requireContext().packageName)
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
        if (hasContactsPermission()) {
            Log.e("askContactsPermission", "Has contacts permission")
            postEvent(
                HomeEvent.ReadContacts
            )
        } else {
            askContactsPermissionDialog()

        }
    }

    private fun hasContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }


    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        { inflater, viewGroup, value ->
            FragmentHomeBinding.inflate(inflater, viewGroup, value)
        }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    override fun onBottomSheetClosedOrDismissed() {
        unSelectSaveLocationDropDown()
    }


}