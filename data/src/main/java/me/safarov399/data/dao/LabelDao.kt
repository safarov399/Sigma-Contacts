package me.safarov399.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.base.BaseDao
import me.safarov399.core.entity.LabelEntity

@Dao
interface LabelDao : BaseDao<LabelEntity> {

    @Insert
    override fun insert(entity: LabelEntity)

    @Query("SELECT * FROM labels")
    override fun getAll(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels WHERE id=:id")
    override fun getById(id: Long): Flow<LabelEntity>

    @Query("DELETE FROM labels WHERE id=:id")
    override fun deleteById(id: Long)
}