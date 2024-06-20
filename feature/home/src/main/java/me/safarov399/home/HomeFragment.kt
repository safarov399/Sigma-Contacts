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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.common.dialogs.PermissionRequestDialog
import me.safarov399.core.adapter.ContactAdapter
import me.safarov399.core.base.BaseFragment
import me.safarov399.home.databinding.FragmentHomeBinding


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel, HomeState, HomeEffect, HomeEvent>() {

    private var contactsAdapter: ContactAdapter? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                postEvent(
                    HomeEvent.ReadContacts
                )
            } else {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    goToSettingsDialog()
                }
                else {
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
        binding.homeRecyclerView.adapter = contactsAdapter
        askContactsPermission()
        binding.homeSearchBar.findViewById<ImageView>(me.safarov399.common.R.id.search_bar_three_dots_icon).setOnClickListener {
            showSelectPopup(it)
        }
        binding.homeFab.setOnClickListener {

        }
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


    private fun showSelectPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val popupMenuInflater = popup.menuInflater
        popupMenuInflater.inflate(me.safarov399.common.R.menu.search_menu, popup.menu)
        popup.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId) {
                me.safarov399.common.R.id.select -> {
                    Toast.makeText(requireContext(), "Select", Toast.LENGTH_SHORT).show()
                }
                me.safarov399.common.R.id.select_all -> {
                    Toast.makeText(requireContext(), "Select all", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popup.show()
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


}