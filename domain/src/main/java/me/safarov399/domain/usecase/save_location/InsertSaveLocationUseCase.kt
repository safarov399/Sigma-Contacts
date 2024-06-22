package me.safarov399.domain.usecase.save_location

import me.safarov399.core.entity.SaveLocationEntity
import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Inject

class InsertSaveLocationUseCase @Inject constructor(
    private val repository: AbstractSaveLocationRepository
) {
    operator fun invoke(saveLocationEntity: SaveLocationEntity) {
        repository.insert(saveLocationEntity)
    }
}