package me.safarov399.core.pojo

import androidx.annotation.DrawableRes

sealed class OrganizeListItem {
    data class OrganizeRowSingleLineModel(
        @DrawableRes val icon: Int,
        val title: String,
    ): OrganizeListItem()

    data class OrganizeRowDoubleLineModel(
        @DrawableRes val icon: Int,
        val title: String,
        val description: String
    ): OrganizeListItem()
}