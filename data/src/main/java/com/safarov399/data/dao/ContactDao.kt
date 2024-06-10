package com.safarov399.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.ContactEntity

@Dao
interface ContactDao {

    @Upsert
    fun insert(contactEntity: ContactEntity)

    @Query("SELECT * FROM contacts WHERE id=:id")
    fun getById(id: Long): Flow<ContactEntity>

    @Query("SELECT * FROM contacts")
    fun getAll(): Flow<List<ContactEntity>>

    @Query("DELETE FROM contacts WHERE id=:id")
    fun deleteById(id: Long)

}