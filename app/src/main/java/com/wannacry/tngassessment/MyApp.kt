package com.wannacry.tngassessment

import android.app.Application
import com.wannacry.tngassessment.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(networkModule)
        }
    }
}