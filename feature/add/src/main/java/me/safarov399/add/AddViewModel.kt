package me.safarov399.add

import me.safarov399.core.base.BaseViewModel

class AddViewModel: BaseViewModel<AddState, AddEffect, AddEvent>() {
    override fun getInitialState(): AddState = AddState()
}