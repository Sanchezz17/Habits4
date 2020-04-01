package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits4.repositories.InMemoryHabitsRepository
import com.example.habits4.ui.home.habits.Habit


class EditHabitViewModel(private val habitId: Int) : ViewModel() {
    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()

    val habit: LiveData<Habit?> = mutableHabit

    init {
        load()
    }

    private fun load() {
        InMemoryHabitsRepository.loadHabit(habitId).let { loadedHabit: Habit? ->
            mutableHabit.postValue(loadedHabit)
        }
    }

    fun saveHabit(habit: Habit) {
        if (habitId == -1) {
            InMemoryHabitsRepository.addHabit(habit)
        } else {
            InMemoryHabitsRepository.updateHabit(habitId, habit)
        }
    }
}