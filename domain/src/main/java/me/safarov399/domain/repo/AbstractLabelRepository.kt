package me.safarov399.domain.repo

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.LabelEntity

interface AbstractLabelRepository {
    fun insert(labelEntity: LabelEntity)
    fun getAll(): Flow<List<LabelEntity>>
    fun getById(id: Long): Flow<LabelEntity>
    fun deleteById(id: Long)
}