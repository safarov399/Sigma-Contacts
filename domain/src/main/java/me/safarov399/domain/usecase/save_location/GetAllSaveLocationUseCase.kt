package me.safarov399.domain.usecase.save_location

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.SaveLocationEntity
import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Inject

class GetAllSaveLocationUseCase @Inject constructor(
    private val repository: AbstractSaveLocationRepository
) {
    operator fun invoke(): Flow<List<SaveLocationEntity>> {
        return repository.getAll()
    }
}