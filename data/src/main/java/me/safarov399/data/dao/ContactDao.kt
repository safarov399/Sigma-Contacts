package me.safarov399.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.base.BaseDao
import me.safarov399.core.entity.ContactEntity

@Dao
interface ContactDao: BaseDao<ContactEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(entity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<ContactEntity>)

    @Query("SELECT * FROM contacts WHERE id=:id")
    override fun getById(id: Long): Flow<ContactEntity>

    @Query("SELECT * FROM contacts")
    override fun getAll(): Flow<List<ContactEntity>>

    @Query("DELETE FROM contacts WHERE id=:id")
    override fun deleteById(id: Long)

}