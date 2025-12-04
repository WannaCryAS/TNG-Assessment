package com.wannacry.tngassessment.domain.usecase

import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(private val repo: UserRepository) {
    fun execute(): Flow<List<User>> = repo.getUsers()
}