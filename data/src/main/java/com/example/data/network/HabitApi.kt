package com.example.data.network

import com.example.data.models.Habit
import com.example.domain.entities.HabitUID
import retrofit2.Response
import retrofit2.http.*


interface HabitApi {
    @GET("habit")
    suspend fun getHabits(): Response<List<Habit>>

    @PUT("habit")
    suspend fun addOrUpdateHabit(@Body habit: Habit): Response<HabitUID>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body habitUID: HabitUID): Response<Unit>
}