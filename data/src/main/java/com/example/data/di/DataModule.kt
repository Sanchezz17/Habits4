package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.data.database.HabitDao
import com.example.data.network.AuthorizationHeaderInterceptor
import com.example.data.network.HabitApi
import com.example.data.network.HabitPriorityTypeAdapter
import com.example.data.network.HabitTypeTypeAdapter
import com.example.data.repositories.HabitsRepositoryImpl
import com.example.domain.entities.enums.HabitPriority
import com.example.domain.entities.enums.HabitType
import com.example.domain.repositories.HabitsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {

    @Provides
    fun provideHabitRepository(habitDao: HabitDao, habitApi: HabitApi): HabitsRepository {
        return HabitsRepositoryImpl(habitDao, habitApi)
    }

    @Provides
    fun provideHabitApi(retrofit: Retrofit): HabitApi {
        return retrofit.create(HabitApi::class.java)
    }

    @Provides
    fun provideHabitDao(database: AppDatabase) = database.habitDao()

    @Provides
    fun provideDatabase(): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java, "HabitsDB"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                HabitType::class.java,
                HabitTypeTypeAdapter()
            )
            .registerTypeAdapter(
                HabitPriority::class.java,
                HabitPriorityTypeAdapter()
            )
            .create()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(AuthorizationHeaderInterceptor())
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}