package me.safarov399.organize

import me.safarov399.core.base.BaseViewModel

class OrganizeViewModel: BaseViewModel<OrganizeState, OrganizeEffect, OrganizeEvent>() {
    override fun getInitialState(): OrganizeState = OrganizeState()
}