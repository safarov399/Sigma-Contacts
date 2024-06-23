package me.safarov399.domain.usecase.save_location

import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Inject

class DeleteByIdSaveLocationUseCase @Inject constructor(
    private val repository: AbstractSaveLocationRepository
) {
    operator fun invoke(id: Long) {
        repository.deleteById(id)
    }
}