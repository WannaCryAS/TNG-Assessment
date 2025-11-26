package com.wannacry.tngassessment.domain.service

import com.wannacry.tngassessment.domain.data.User
import retrofit2.http.GET

interface UserServiceApi {
    @GET("/users")
    suspend fun getUsers(): List<User>

}