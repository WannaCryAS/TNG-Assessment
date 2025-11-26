package com.wannacry.tngassessment.domain.repo

import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.service.UserServiceApi

class UserRepositoryImpl(private val service: UserServiceApi): UserRepository {
    override suspend fun getUsers(): List<User> {
        return service.getUsers().map {
            User(
                id = it.id,
                name = it.name,
                username = it.username,
                email = it.email,
                address = it.address,
                phone = it.phone,
                website = it.website,
                company = it.company
            )
        }
    }

}