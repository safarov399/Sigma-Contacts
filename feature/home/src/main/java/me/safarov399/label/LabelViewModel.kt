package me.safarov399.label

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import me.safarov399.core.base.BaseViewModel
import me.safarov399.domain.usecase.label.GetAllLabelsUseCase
import javax.inject.Inject

@HiltViewModel
class LabelViewModel @Inject constructor(
    private val getAllLabelsUseCase: GetAllLabelsUseCase
) : BaseViewModel<LabelState, LabelEffect, LabelEvent>() {
    override fun getInitialState(): LabelState = LabelState(listOf())

    override fun onEventUpdate(event: LabelEvent) {
        when(event) {
            LabelEvent.LoadAllLabels -> viewModelScope.launch(Dispatchers.IO) {
                getAllLabelsUseCase.invoke().onEach {
                    setState(
                        getCurrentState().copy(
                            labels = it
                        )
                    )
                }.collect()
            }
        }
    }


}