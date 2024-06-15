package com.safarov399.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.ContactEntity

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<ContactEntity>)

    @Query("SELECT * FROM contacts WHERE id=:id")
    fun getById(id: Long): Flow<ContactEntity>

    @Query("SELECT * FROM contacts")
    fun getAll(): Flow<List<ContactEntity>>

    @Query("DELETE FROM contacts WHERE id=:id")
    fun deleteById(id: Long)

}