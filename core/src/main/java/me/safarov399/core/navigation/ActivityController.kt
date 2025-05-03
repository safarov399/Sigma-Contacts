package me.safarov399.core.navigation

interface ActivityController {
    fun navigateToMainActivity()
    fun navigateToFullScreenActivity(destinationId: Int, dataId: Long = 0)
    fun toggleMoreVertVisibility(visibility: Int)
}