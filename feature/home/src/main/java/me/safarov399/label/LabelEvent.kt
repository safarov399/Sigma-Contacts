package me.safarov399.label

sealed class LabelEvent {
    data object LoadAllLabels: LabelEvent()
}