package com.wannacry.tngassessment.di

import com.wannacry.tngassessment.config.Constants.BASE_URL
import com.wannacry.tngassessment.domain.repo.UserRepository
import com.wannacry.tngassessment.domain.repo.UserRepositoryImpl
import com.wannacry.tngassessment.domain.service.UserServiceApi
import com.wannacry.tngassessment.domain.usecase.GetUserUseCase
import com.wannacry.tngassessment.presentation.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserServiceApi::class.java)
    }
//    single { Service(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { GetUserUseCase(get()) }
    viewModel { UsersViewModel(get()) }

}