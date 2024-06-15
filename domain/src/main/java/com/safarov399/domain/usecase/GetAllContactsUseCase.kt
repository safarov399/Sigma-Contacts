package com.safarov399.domain.usecase

import com.safarov399.domain.repo.AbstractContactRepository
import kotlinx.coroutines.flow.Flow
import me.safarov399.core.entity.ContactEntity
import javax.inject.Inject


class GetAllContactsUseCase @Inject constructor(
    private val contactRepository: AbstractContactRepository
) {
    operator fun invoke(): Flow<List<ContactEntity>> {
        return contactRepository.getAll()
    }
}