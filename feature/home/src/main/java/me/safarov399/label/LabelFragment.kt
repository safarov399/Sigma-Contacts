package me.safarov399.label

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.core.adapter.LabelAdapter
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.entity.LabelEntity
import me.safarov399.home.databinding.FragmentLabelBinding

@AndroidEntryPoint
class LabelFragment: BaseFragment<FragmentLabelBinding, LabelViewModel, LabelState, LabelEffect, LabelEvent>() {

    private var labelAdapter: LabelAdapter? = null

    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLabelBinding = {
            inflater, viewGroup, value -> FragmentLabelBinding.inflate(inflater, viewGroup, value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        postEvent(LabelEvent.LoadAllLabels)
    }

    private fun configureViews() {
        labelAdapter = LabelAdapter()
        binding.labelFragmentRecyclerView.adapter = labelAdapter
        labelAdapter?.submitList(labelsExample())
//        labelAdapter?.submitList(listOf())

    }

    override fun onStateUpdate(state: LabelState) {
        labelAdapter?.submitList(labelsExample())
//        labelAdapter?.submitList(state.labels)

    }

    private fun labelsExample(): List<LabelEntity> {
        val label = LabelEntity(
            name = "Family",
            numberOfContacts = 4
        )
        return listOf(label,label,label,label,label,label,label,label,label)
    }

    override fun getViewModelClass(): Class<LabelViewModel> = LabelViewModel::class.java

    override fun onDestroy() {
        super.onDestroy()
        labelAdapter = null
    }
}