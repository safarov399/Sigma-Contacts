package me.safarov399.domain.usecase.contact

import me.safarov399.domain.repo.AbstractContactRepository
import javax.inject.Inject

class DeleteByIdContactUseCase @Inject constructor(
    private val contactRepository: AbstractContactRepository
) {
    operator fun invoke(id: Long) {
        contactRepository.deleteById(id)
    }
}