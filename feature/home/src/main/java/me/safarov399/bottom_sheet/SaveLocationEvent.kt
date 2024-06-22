package me.safarov399.bottom_sheet


sealed class SaveLocationEvent {
    data object LoadAllSaveLocations: SaveLocationEvent()
}