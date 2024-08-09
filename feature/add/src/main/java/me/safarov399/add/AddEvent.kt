package me.safarov399.add

import me.safarov399.core.entity.ContactEntity

sealed class AddEvent {
    data class InsertContact(val contactEntity: ContactEntity): AddEvent()
}