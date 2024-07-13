package me.safarov399.core.base

import kotlinx.coroutines.flow.Flow

interface BaseDao<T: BaseEntity> {
    fun insert(entity: T)
    fun getAll(): Flow<List<T>>
    fun getById(id: Long): Flow<T>
    fun deleteById(id: Long)
}