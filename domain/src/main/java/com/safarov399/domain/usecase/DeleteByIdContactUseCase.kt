package com.safarov399.domain.usecase

import com.safarov399.domain.repo.AbstractContactRepository
import javax.inject.Inject

class DeleteByIdContactUseCase @Inject constructor(
    private val contactRepository: AbstractContactRepository
) {
    operator fun invoke(id: Long) {
        contactRepository.deleteById(id)
    }
}