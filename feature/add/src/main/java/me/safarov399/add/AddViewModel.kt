package me.safarov399.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import me.safarov399.domain.usecase.contact.GetByIdContactUseCase
import me.safarov399.domain.usecase.contact.InsertContactUseCase
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val insertContactUseCase: InsertContactUseCase,
    private val getByIdContactUseCase: GetByIdContactUseCase
) : BaseViewModel<AddState, AddEffect, AddEvent>() {
    override fun getInitialState(): AddState = AddState(ContactEntity())

    override fun onEventUpdate(event: AddEvent) {
        when (event) {
            is AddEvent.InsertContact -> insertContact(event.contactEntity)
            is AddEvent.LoadContact -> viewModelScope.launch(Dispatchers.IO) {
                getByIdContactUseCase.invoke(event.dataId).onEach {
                    setState(
                        getCurrentState().copy(
                            contact = it
                        )
                    )
                }.collect()
            }
        }
    }

    private fun insertContact(contactEntity: ContactEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertContactUseCase.invoke(contactEntity)
        }
    }
}