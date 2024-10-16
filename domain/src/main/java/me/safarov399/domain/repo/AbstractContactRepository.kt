package me.safarov399.domain.repo

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.ContactEntity

interface AbstractContactRepository {
    fun insert(contactEntity: ContactEntity)
    fun insertContacts(contacts: List<ContactEntity>)
    fun getById(id: Long): Flow<ContactEntity>
    fun getAll(): Flow<List<ContactEntity>>
    fun deleteById(id: Long)
}