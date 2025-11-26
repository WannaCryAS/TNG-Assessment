package com.wannacry.tngassessment.domain.usecase

import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.repo.UserRepository

class GetUserUseCase(private val repo: UserRepository) {
    suspend fun execute(): List<User> = repo.getUsers()
}