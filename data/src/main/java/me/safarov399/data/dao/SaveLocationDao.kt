package me.safarov399.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.base.BaseDao
import me.safarov399.core.entity.SaveLocationEntity

@Dao
interface SaveLocationDao: BaseDao<SaveLocationEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(entity: SaveLocationEntity)

    @Query("SELECT * FROM save_locations")
    override fun getAll(): Flow<List<SaveLocationEntity>>

    @Query("SELECT * FROM save_locations WHERE id=:id")
    override fun getById(id: Long): Flow<SaveLocationEntity>

    @Query("DELETE FROM save_locations WHERE id=:id")
    override fun deleteById(id: Long)
}