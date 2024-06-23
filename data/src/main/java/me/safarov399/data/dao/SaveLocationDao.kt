package me.safarov399.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.SaveLocationEntity

@Dao
interface SaveLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(saveLocationEntity: SaveLocationEntity)

    @Query("SELECT * FROM save_locations")
    fun getAll(): Flow<List<SaveLocationEntity>>

    @Query("SELECT * FROM save_locations WHERE id=:id")
    fun getById(id: Long): Flow<SaveLocationEntity>

    @Query("DELETE FROM save_locations WHERE id=:id")
    fun deleteById(id: Long)
}