package me.safarov399.save_location


sealed class SaveLocationEvent {
    data object LoadAllSaveLocations: SaveLocationEvent()
}