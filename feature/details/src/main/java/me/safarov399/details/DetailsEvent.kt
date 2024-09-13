package me.safarov399.details

sealed class DetailsEvent {
    data class LoadContact(val id: Long): DetailsEvent()
    data class DeleteContact(val id: Long, val number: String, val displayName: String): DetailsEvent()
}