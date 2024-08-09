package me.safarov399.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import me.safarov399.domain.usecase.contact.InsertContactUseCase
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val insertContactUseCase: InsertContactUseCase
) : BaseViewModel<AddState, AddEffect, AddEvent>() {
    override fun getInitialState(): AddState = AddState()

    override fun onEventUpdate(event: AddEvent) {
        when (event) {
            is AddEvent.InsertContact -> insertContact(event.contactEntity)
        }
    }

    private fun insertContact(contactEntity: ContactEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertContactUseCase.invoke(contactEntity)
        }
    }
}