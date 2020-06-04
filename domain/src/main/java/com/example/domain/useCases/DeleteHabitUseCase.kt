package com.example.domain.useCases

import com.example.domain.entities.HabitEntity
import com.example.domain.repositories.HabitsRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val habitsRepository: HabitsRepository) {

    suspend fun deleteHabit(habitEntity: HabitEntity) = habitsRepository.deleteHabit(habitEntity)
}