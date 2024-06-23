package me.safarov399.data.repo

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.SaveLocationEntity
import me.safarov399.data.dao.SaveLocationDao
import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Inject

class SaveLocationRepository @Inject constructor(
    private val saveLocationDao: SaveLocationDao
) : AbstractSaveLocationRepository {
    override fun insert(saveLocationEntity: SaveLocationEntity) {
        saveLocationDao.insert(saveLocationEntity)
    }

    override fun getAll(): Flow<List<SaveLocationEntity>> {
        return saveLocationDao.getAll()
    }

    override fun getById(id: Long): Flow<SaveLocationEntity> {
        return saveLocationDao.getById(id)
    }

    override fun deleteById(id: Long) {
        saveLocationDao.deleteById(id)
    }
}