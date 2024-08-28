package me.safarov399.core.adapter

import me.safarov399.core.entity.ContactEntity

interface OnClickListener {
    fun onClick(position: Int, model: ContactEntity)
}