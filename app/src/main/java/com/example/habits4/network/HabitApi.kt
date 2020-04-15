package com.example.habits4.network

import com.example.habits4.model.Error
import com.example.habits4.model.HabitUID
import com.example.habits4.model.Habit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface HabitApi {
    @GET("habits")
    suspend fun getHabits(): Response<List<Habit>>

    @PUT("habits")
    suspend fun addOrUpdateHabit(): Response<HabitUID>

    @DELETE("habits")
    suspend fun deleteHabit(@Body habitUIDDto: HabitUID): Response<Error>
}