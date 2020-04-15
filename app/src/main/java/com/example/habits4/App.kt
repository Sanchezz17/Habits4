package com.example.habits4

import android.app.Application
import androidx.room.Room
import com.example.habits4.database.AppDatabase
import com.example.habits4.model.enums.HabitPriority
import com.example.habits4.model.enums.HabitType
import com.example.habits4.network.AuthorizationHeaderInterceptor
import com.example.habits4.network.HabitApi
import com.example.habits4.network.HabitPriorityTypeAdapter
import com.example.habits4.network.HabitTypeTypeAdapter
import com.example.habits4.repository.HabitsRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
        lateinit var habitApi: HabitApi
        lateinit var habitsRepository: HabitsRepository
    }

    override fun onCreate() {
        super.onCreate()

        database = Room
                .databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "HabitsDB"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(HabitType::class.java, HabitTypeTypeAdapter())
            .registerTypeAdapter(HabitPriority::class.java, HabitPriorityTypeAdapter())
            .create()

        val okHttpClient= OkHttpClient().newBuilder()
            .addInterceptor(AuthorizationHeaderInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        habitApi = retrofit.create(HabitApi::class.java)

        habitsRepository = HabitsRepository(database.habitDao(), habitApi)
    }
}