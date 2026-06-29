package com.example.testcity

import android.app.Application
import com.example.testcity.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestCityApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestCityApplication)
            modules(appModules)
        }
    }
}
