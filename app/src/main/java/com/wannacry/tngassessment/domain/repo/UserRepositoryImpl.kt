package com.wannacry.tngassessment.domain.repo

import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.service.UserServiceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val service: UserServiceApi): UserRepository {
    override fun getUsers(): Flow<List<User>> = flow {
        emit(service.getUsers())
    }
}