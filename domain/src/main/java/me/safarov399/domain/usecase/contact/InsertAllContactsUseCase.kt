package me.safarov399.domain.usecase.contact

import me.safarov399.domain.repo.AbstractContactRepository
import me.safarov399.core.entity.ContactEntity
import javax.inject.Inject

class InsertAllContactsUseCase @Inject constructor(
    private val contactRepository: AbstractContactRepository
) {
    operator fun invoke(contacts: List<ContactEntity>) {
        contactRepository.insertContacts(contacts)
    }
}