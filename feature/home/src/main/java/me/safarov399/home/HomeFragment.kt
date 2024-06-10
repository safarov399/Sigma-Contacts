package me.safarov399.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import me.safarov399.core.adapter.ContactAdapter
import me.safarov399.core.base.BaseFragment
import me.safarov399.home.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel, HomeState, HomeEffect, HomeEvent>() {

    private var contactsAdapter: ContactAdapter? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                postEvent(
                    HomeEvent.ReadContacts
                )
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactsAdapter = ContactAdapter()
        binding.homeRecyclerView.adapter = contactsAdapter
        askContactsPermission()

    }


    private fun askContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            postEvent(
                HomeEvent.ReadContacts
            )
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
        }
    }


    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        { inflater, viewGroup, value ->
            FragmentHomeBinding.inflate(inflater, viewGroup, value)
        }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java


}