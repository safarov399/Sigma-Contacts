package me.safarov399.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.safarov399.core.adapter.ContactAdapter
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.entity.ContactEntity
import me.safarov399.home.databinding.FragmentHomeBinding

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel, HomeState, HomeEffect, HomeEvent>() {

    private var contactsAdapter: ContactAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactsAdapter = ContactAdapter()
        binding.homeRecyclerView.adapter = contactsAdapter
        contactsAdapter?.submitList(mockContactsList())
    }

    private fun mockContactsList(): ArrayList<ContactEntity> {
        val contact1 = ContactEntity(
            id = 1,
            name = "Jack",
            emailList = hashSetOf("smth@gmail.com"),
            numberList = hashSetOf("00000000"),
        )
        val contact2 = ContactEntity(
            id = 2,
            name = "Mike",
            emailList = hashSetOf("sigma@gmail.com"),
            numberList = hashSetOf("00000000"),
            profilePhoto = "reddit_color"
        )

        return arrayListOf(contact1,contact1,contact1,contact1,contact1,contact2,contact1,contact2,contact2,contact1,contact1,contact1,contact1,contact1,contact1,contact1,contact1,contact1,contact1,contact2,contact2,contact2,contact2,contact1)
    }


    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        { inflater, viewGroup, value ->
            FragmentHomeBinding.inflate(inflater, viewGroup, value)
        }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java


}