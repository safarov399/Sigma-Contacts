package com.safarov399.data.repo

import com.safarov399.data.dao.ContactDao
import com.safarov399.domain.repo.AbstractContactRepository
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.ContactEntity
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) : AbstractContactRepository {
    override fun insert(contactEntity: ContactEntity) {
        contactDao.insert(contactEntity)
    }

    override fun insertContacts(contacts: List<ContactEntity>) {
        contactDao.insertContacts(contacts)
    }


    override fun getById(id: Long): Flow<ContactEntity> {
        return contactDao.getById(id)
    }

    override fun getAll(): Flow<List<ContactEntity>> {
        return contactDao.getAll()
    }

    override fun deleteById(id: Long) {
        contactDao.deleteById(id)
    }
}