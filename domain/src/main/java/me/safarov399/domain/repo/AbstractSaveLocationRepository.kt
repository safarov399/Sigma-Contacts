package me.safarov399.domain.repo

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.SaveLocationEntity

interface AbstractSaveLocationRepository {
    fun insert(saveLocationEntity: SaveLocationEntity)
    fun getAll(): Flow<List<SaveLocationEntity>>
    fun getById(id: Long): Flow<SaveLocationEntity>
    fun deleteById(id: Long)
}