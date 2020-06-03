package com.example.domain.repositories

import com.example.domain.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {
    suspend fun initializeHabitsInDB()

    suspend fun addOrUpdateHabit(habitEntity: HabitEntity)

    suspend fun deleteHabit(habitEntity: HabitEntity)

    fun getAllHabits(): Flow<List<HabitEntity>>

    fun getHabitByUid(uid: String?): Flow<HabitEntity?>
}