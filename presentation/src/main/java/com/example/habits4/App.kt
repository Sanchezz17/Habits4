package com.example.habits4

import android.app.Application
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.domain.enums.HabitPriority
import com.example.domain.enums.HabitType
import com.example.data.network.AuthorizationHeaderInterceptor
import com.example.data.network.HabitApi
import com.example.data.network.HabitPriorityTypeAdapter
import com.example.data.network.HabitTypeTypeAdapter
import com.example.data.repositories.HabitsRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App : Application() {
    companion object {
        lateinit var habitsRepository: HabitsRepository
    }

    override fun onCreate() {
        super.onCreate()

        val database = Room
                .databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "HabitsDB"
        )
            .fallbackToDestructiveMigration()
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(HabitType::class.java,
                HabitTypeTypeAdapter()
            )
            .registerTypeAdapter(HabitPriority::class.java,
                HabitPriorityTypeAdapter()
            )
            .create()

        val okHttpClient= OkHttpClient().newBuilder()
            .addInterceptor(AuthorizationHeaderInterceptor())
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val habitApi = retrofit.create(HabitApi::class.java)

        habitsRepository = HabitsRepository(
            database.habitDao(),
            habitApi
        )
    }
}