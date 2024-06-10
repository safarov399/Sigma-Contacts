package me.safarov399.home

import me.safarov399.core.entity.ContactEntity

data class HomeState(
    val contactEntity: List<ContactEntity>
)