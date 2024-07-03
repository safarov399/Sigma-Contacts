package me.safarov399.organize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safarov399.organize.databinding.FragmentOrganizeBinding
import me.safarov399.core.adapter.OrganizeRowViewAdapter
import me.safarov399.core.base.BaseFragment
import me.safarov399.core.pojo.OrganizeRowModel

class OrganizeFragment : BaseFragment<FragmentOrganizeBinding, OrganizeViewModel, OrganizeState, OrganizeEffect, OrganizeEvent>() {

    private var adapter: OrganizeRowViewAdapter? = null

    override fun getViewModelClass(): Class<OrganizeViewModel> = OrganizeViewModel::class.java

    override val getViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrganizeBinding =
        { inflater, viewGroup, value ->
            FragmentOrganizeBinding.inflate(inflater, viewGroup, value)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrganizeRowViewAdapter()
        binding.organizeFragmentThisDeviceRecyclerView.adapter = adapter
        adapter?.submitList(thisDeviceOrganizeRowList())
    }

    private fun thisDeviceOrganizeRowList(): List<OrganizeRowModel> {
        val organize1 = OrganizeRowModel(
            icon = "gs_ringtone_cast_vd_theme_24",
            title = "Contact ringtones",
            description = "Set ringtones for specific contacts"
        )
        val organize2 = OrganizeRowModel(
            icon = "quantum_gm_ic_sim_card_vd_theme_24",
            title = "Manage SIM",
            description = "Import or delete contacts from SIM"
        )
        val organize3 = OrganizeRowModel(
            icon = "gs_download_vd_theme_24",
            title = "Import from file",
            description = ""
        )
        val organize4 = OrganizeRowModel(
            icon = "gs_upload_vd_theme_24",
            title = "Export to file",
            description = ""
        )
        val organize5 = OrganizeRowModel(
            icon = "quantum_gm_ic_block_vd_theme_24",
            title = "Blocked numbers",
            description = "Numbers you won't receive calls or texts from"
        )
        val organize6 = OrganizeRowModel(
            icon = "gs_settings_vd_theme_24",
            title = "Settings",
            description = ""
        )

        return listOf(organize1, organize2, organize3, organize4, organize5, organize6)
    }


}