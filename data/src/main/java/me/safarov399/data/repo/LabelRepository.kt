package me.safarov399.data.repo

import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.LabelEntity
import me.safarov399.data.dao.LabelDao
import me.safarov399.domain.repo.AbstractLabelRepository
import javax.inject.Inject

class LabelRepository @Inject constructor(
    private val dao: LabelDao
): AbstractLabelRepository {
    override fun insert(labelEntity: LabelEntity) {
        dao.insert(labelEntity)
    }

    override fun getAll(): Flow<List<LabelEntity>> {
        return dao.getAll()
    }

    override fun getById(id: Long): Flow<LabelEntity> {
        return dao.getById(id)
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}