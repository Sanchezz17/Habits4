package com.example.habits4

import android.app.Application
import androidx.room.Room
import com.example.habits4.database.AppDatabase


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room
                .databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "HabitsDB"
        )
            .allowMainThreadQueries()
            .build()
    }
}