package me.safarov399.domain.usecase.label

import me.safarov399.domain.repo.AbstractLabelRepository
import javax.inject.Inject

class DeleteByIdLabelUseCase @Inject constructor(
    private val repository: AbstractLabelRepository
) {
    operator fun invoke(id: Long) {
        repository.deleteById(id)
    }
}