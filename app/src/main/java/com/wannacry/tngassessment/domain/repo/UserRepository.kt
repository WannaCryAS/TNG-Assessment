package com.wannacry.tngassessment.domain.repo

import com.wannacry.tngassessment.domain.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
}