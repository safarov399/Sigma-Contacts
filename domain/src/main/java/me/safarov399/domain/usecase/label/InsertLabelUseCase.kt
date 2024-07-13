package me.safarov399.domain.usecase.label

import me.safarov399.core.entity.LabelEntity
import me.safarov399.domain.repo.AbstractLabelRepository
import javax.inject.Inject

class InsertLabelUseCase @Inject constructor(
    private val repository: AbstractLabelRepository
) {
    operator fun invoke(label: LabelEntity) {
        repository.insert(label)
    }
}