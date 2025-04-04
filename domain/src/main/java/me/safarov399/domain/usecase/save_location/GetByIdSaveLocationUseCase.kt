package me.safarov399.domain.usecase.save_location

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.SaveLocationEntity
import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Inject

class GetByIdSaveLocationUseCase @Inject constructor(
    private val repository: AbstractSaveLocationRepository
) {
    operator fun invoke(id: Long): Flow<SaveLocationEntity> {
        return repository.getById(id)
    }
}