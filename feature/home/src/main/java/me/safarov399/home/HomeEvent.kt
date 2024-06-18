package me.safarov399.home

sealed class HomeEvent {
    data object ReadContacts: HomeEvent()
}