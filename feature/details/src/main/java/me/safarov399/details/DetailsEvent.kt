package me.safarov399.details

sealed class DetailsEvent {
    data class LoadContact(val id: Long): DetailsEvent()
}