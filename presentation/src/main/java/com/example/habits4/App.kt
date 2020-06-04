package com.example.habits4

import android.app.Application
import com.example.data.di.DataModule
import com.example.habits4.di.ApplicationComponent
import com.example.habits4.di.DaggerApplicationComponent


class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .dataModule(DataModule(this))
            .build()
    }
}