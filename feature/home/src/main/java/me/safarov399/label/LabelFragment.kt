package me.safarov399.label

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.safarov399.core.adapter.label.LabelAdapter
import me.safarov399.core.adapter.label.LabelHeaderAdapter
import me.safarov399.core.annotations.MockData
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.entity.LabelEntity
import me.safarov399.core.pojo.LabelHeaderItem
import me.safarov399.home.databinding.FragmentLabelBinding

@AndroidEntryPoint
class LabelFragment : BaseFragment<FragmentLabelBinding, LabelViewModel, LabelState, LabelEffect, LabelEvent>() {

    private var labelAdapter: LabelAdapter? = null
    private var labelHeaderAdapter: LabelHeaderAdapter? = null
    private var concatAdapter: ConcatAdapter? = null

    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLabelBinding = { inflater, viewGroup, value ->
        FragmentLabelBinding.inflate(inflater, viewGroup, value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        postEvent(LabelEvent.LoadAllLabels)
    }

    private fun configureViews() {
        labelAdapter = LabelAdapter()
        labelHeaderAdapter = LabelHeaderAdapter()
        concatAdapter = ConcatAdapter(labelHeaderAdapter, labelAdapter)
        binding.labelFragmentRecyclerView.adapter = concatAdapter
        labelHeaderAdapter?.submitList(
            listOf(
                LabelHeaderItem()
            )
        )



    }

    override fun onStateUpdate(state: LabelState) {
        labelAdapter?.submitList(state.labels)

    }

    @MockData
    private fun labelsExample(): List<LabelEntity> {
        val label = LabelEntity(
            name = "Family",
            numberOfContacts = 4
        )
        return listOf(label, label, label, label, label, label, label, label, label)
    }

    override fun getViewModelClass(): Class<LabelViewModel> = LabelViewModel::class.java

    override fun onDestroy() {
        super.onDestroy()
        labelAdapter = null
    }
}