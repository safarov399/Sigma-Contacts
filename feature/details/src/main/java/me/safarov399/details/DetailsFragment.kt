package me.safarov399.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuCompat
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.exception.InvalidContactIdException
import me.safarov399.details.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel, DetailsState, DetailsEffect, DetailsEvent>() {


    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding = { inflater, viewGroup, value ->
        FragmentDetailsBinding.inflate(inflater, viewGroup, value)
    }
    private var dataId: Long = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            detailsBackButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            detailsHorizontalLine.setBackgroundColor(resources.getColor(me.safarov399.common.R.color.gray, null))
            detailsThreeDotsIv.setOnClickListener {
                showEditPopUp(it)
            }
        }


        if (dataId != 0.toLong()) {
            loadContactData(dataId)
        } else {
            @Suppress("KotlinConstantConditions") throw InvalidContactIdException("Contact with id $dataId does not exist")
        }
    }

    private fun showEditPopUp(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val popupInflater = popup.menuInflater
        popupInflater.inflate(me.safarov399.common.R.menu.contact_details_menu, popup.menu)
        MenuCompat.setGroupDividerEnabled(popup.menu, true);
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                me.safarov399.common.R.id.contact_details_menu_share -> {

                }

                me.safarov399.common.R.id.contact_details_menu_sim -> {

                }

                me.safarov399.common.R.id.contact_details_menu_ringtone -> {

                }

                me.safarov399.common.R.id.contact_details_menu_add_home_screen -> {

                }

                me.safarov399.common.R.id.contact_details_menu_move_to_another_account -> {

                }

                me.safarov399.common.R.id.contact_details_menu_delete -> {

                }

                me.safarov399.common.R.id.contact_details_menu_help_and_feedback -> {

                }
            }
            true
        }
        popup.show()
    }

    override fun getViewModelClass(): Class<DetailsViewModel> = DetailsViewModel::class.java
    override fun onStateUpdate(state: DetailsState) {
        binding.apply {
            detailsContactNameTv.text = state.contact.firstName
            detailsContactInfoClContactNumberTv.text = state.contact.numbers.first()
            if (state.contact.emails.isNotEmpty()) {
                detailsContactInfoClEmailTv.text = state.contact.emails.first()
            } else {
                detailsContactInfoClEmailIv.visibility = View.GONE
                detailsContactInfoClEmailTv.visibility = View.GONE
                detailsContactInfoClEmailTypeTv.visibility = View.GONE
            }

        }
    }

    private fun loadContactData(dataId: Long) {
        postEvent(DetailsEvent.LoadContact(dataId))
    }

    fun setDataId(id: Long) {
        dataId = id
    }
}