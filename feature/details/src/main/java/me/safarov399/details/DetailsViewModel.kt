package me.safarov399.details

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.safarov399.core.base.BaseViewModel
import me.safarov399.core.entity.ContactEntity
import me.safarov399.domain.usecase.contact.GetByIdContactUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getByIdContactUseCase: GetByIdContactUseCase
): BaseViewModel<DetailsState, DetailsEffect,DetailsEvent>() {
    override fun getInitialState(): DetailsState = DetailsState(
        ContactEntity(
            numbers = mutableListOf(""),
            emails = mutableListOf("")
        )
    )

    override fun onEventUpdate(event: DetailsEvent) {
        when(event) {
            is DetailsEvent.LoadContact -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getByIdContactUseCase.invoke(event.id).onEach {
                        setState(
                            getCurrentState().copy(
                                contact = it
                            )
                        )
                    }.collect()
                }
            }
        }
    }
}