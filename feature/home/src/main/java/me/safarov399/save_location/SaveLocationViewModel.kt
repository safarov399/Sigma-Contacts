package me.safarov399.save_location

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.safarov399.core.base.BaseViewModel
import me.safarov399.domain.usecase.save_location.GetAllSaveLocationUseCase
import me.safarov399.domain.usecase.save_location.InsertSaveLocationUseCase
import javax.inject.Inject

@HiltViewModel
class SaveLocationViewModel @Inject constructor (
    private val getAllSaveLocationUseCase: GetAllSaveLocationUseCase,
    private val insertSaveLocationUseCase: InsertSaveLocationUseCase
): BaseViewModel<SaveLocationState, SaveLocationEffect, SaveLocationEvent>() {
    override fun getInitialState(): SaveLocationState = SaveLocationState(listOf())

    override fun onEventUpdate(event: SaveLocationEvent) {
        when(event) {
            SaveLocationEvent.LoadAllSaveLocations -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getAllSaveLocationUseCase.invoke().onEach {
                        setState(
                            getCurrentState().copy(
                                saves = it
                            )
                        )
                    }.collect()
                }
            }
        }
    }
}