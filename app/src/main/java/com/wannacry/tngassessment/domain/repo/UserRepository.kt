package com.wannacry.tngassessment.domain.repo

import com.wannacry.tngassessment.domain.data.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}