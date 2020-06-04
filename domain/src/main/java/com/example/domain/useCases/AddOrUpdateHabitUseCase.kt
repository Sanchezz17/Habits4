package com.example.domain.useCases

import com.example.domain.entities.HabitEntity
import com.example.domain.repositories.HabitsRepository
import javax.inject.Inject

class AddOrUpdateHabitUseCase @Inject constructor(private val habitsRepository: HabitsRepository) {

    suspend fun addOrUpdateHabit(habitEntity: HabitEntity) = habitsRepository.addOrUpdateHabit(habitEntity)
}