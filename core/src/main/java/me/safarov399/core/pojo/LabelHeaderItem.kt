package me.safarov399.core.pojo

import androidx.annotation.DrawableRes

data class LabelHeaderItem(
    @DrawableRes var img: Int = me.safarov399.common.R.drawable.add,
    var label: String = "New label"
)