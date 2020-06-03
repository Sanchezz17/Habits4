package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.habits4.App
import com.example.data.models.Habit
import com.example.data.repositories.HabitsRepositoryImpl
import com.example.domain.entities.HabitEntity
import kotlinx.coroutines.*


class EditHabitViewModel(
    habitUid: String?,
    private val habitsRepository: HabitsRepositoryImpl = App.habitsRepository
) : ViewModel() {
    val habit: LiveData<HabitEntity?> = habitsRepository.getHabitByUid(habitUid).asLiveData()

    fun saveHabit(habitEntity: HabitEntity) = viewModelScope.launch(Dispatchers.Default) {
        habitsRepository.addOrUpdateHabit(habitEntity)
    }
}