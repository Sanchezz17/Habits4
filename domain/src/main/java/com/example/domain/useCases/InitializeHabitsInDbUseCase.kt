package com.example.domain.useCases

import com.example.domain.repositories.HabitsRepository
import javax.inject.Inject

class InitializeHabitsInDbUseCase @Inject constructor(private val habitsRepository: HabitsRepository) {

    suspend fun initializeHabitsInDB() = habitsRepository.initializeHabitsInDB()
}