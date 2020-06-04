package com.example.domain.useCases

import com.example.domain.entities.HabitEntity
import com.example.domain.repositories.HabitsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor(private val habitsRepository: HabitsRepository) {

    fun getAllHabits(): Flow<List<HabitEntity>> = habitsRepository.getAllHabits()
}