package com.safarov399.domain.usecase

import com.safarov399.domain.repo.AbstractContactRepository
import me.safarov399.core.entity.ContactEntity
import javax.inject.Inject

class InsertContactUseCase @Inject constructor(
    private val contactRepository: AbstractContactRepository
) {
    operator fun invoke(contact: ContactEntity) {
        contactRepository.insert(contact)
    }
}