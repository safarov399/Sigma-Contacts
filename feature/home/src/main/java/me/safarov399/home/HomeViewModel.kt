package me.safarov399.home

import me.safarov399.core.BaseViewModel

class HomeViewModel: BaseViewModel<HomeState, HomeEffect, HomeEvent>() {
    override fun getInitialState(): HomeState = HomeState(arrayListOf())
}