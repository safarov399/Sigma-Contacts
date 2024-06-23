package me.safarov399.bottom_sheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.core.adapter.SaveLocationBottomSheetAdapter
import me.safarov399.core.base.BaseFragment
import me.safarov399.home.HomeFragment
import me.safarov399.home.databinding.FragmentSaveLocationBinding

@AndroidEntryPoint
class SaveLocationFragment : BaseFragment<FragmentSaveLocationBinding, SaveLocationViewModel, SaveLocationState, SaveLocationEffect, SaveLocationEvent>() {

    private var adapter: SaveLocationBottomSheetAdapter? = null

    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSaveLocationBinding = {
        inflater, viewGroup, value -> FragmentSaveLocationBinding.inflate(inflater, viewGroup, value)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        postEvent(SaveLocationEvent.LoadAllSaveLocations)
    }


    override fun getViewModelClass(): Class<SaveLocationViewModel> = SaveLocationViewModel::class.java

    private fun configureViews() {
        adapter = SaveLocationBottomSheetAdapter()
        binding.saveLocationBottomSheetDialogRecyclerView.adapter = adapter
        adapter?.submitList(listOf())
    }

    override fun onStateUpdate(state: SaveLocationState) {
        adapter?.submitList(state.saves)
        Log.e("SaveLocationFragment onStateUpdate", state.saves.toString())

    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

}