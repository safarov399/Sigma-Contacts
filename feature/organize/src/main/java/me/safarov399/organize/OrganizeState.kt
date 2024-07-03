package me.safarov399.organize

import me.safarov399.core.pojo.OrganizeRowModel

data class OrganizeState(
    val organizeThisDeviceList: List<OrganizeRowModel> = listOf(),
    val organizeGoogleAccountList: List<OrganizeRowModel> = listOf()
)