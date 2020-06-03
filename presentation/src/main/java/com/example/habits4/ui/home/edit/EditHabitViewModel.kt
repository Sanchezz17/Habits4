package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habits4.App
import com.example.data.model.Habit
import com.example.data.repositories.HabitsRepository
import kotlinx.coroutines.*


class EditHabitViewModel(
    habitUid: String?,
    private val habitsRepository: HabitsRepository = App.habitsRepository
) : ViewModel() {
    val habit: LiveData<Habit?> = habitsRepository.getHabitByUid(habitUid)

    fun saveHabit(habit: Habit) = viewModelScope.launch(Dispatchers.Default) {
        habitsRepository.addOrUpdateHabit(habit)
    }
}