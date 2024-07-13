package me.safarov399.domain.usecase.label

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.LabelEntity
import me.safarov399.domain.repo.AbstractLabelRepository
import javax.inject.Inject

class GetAllLabelsUseCase @Inject constructor(
    private val repository: AbstractLabelRepository
) {
    operator fun invoke(): Flow<List<LabelEntity>> {
        return repository.getAll()
    }
}