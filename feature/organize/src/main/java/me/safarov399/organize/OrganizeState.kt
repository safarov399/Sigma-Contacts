package me.safarov399.organize

import me.safarov399.core.pojo.OrganizeListItem

data class OrganizeState(
    val organizeThisDeviceList: List<OrganizeListItem> = listOf(),
    val organizeGoogleAccountList: List<OrganizeListItem> = listOf()
)