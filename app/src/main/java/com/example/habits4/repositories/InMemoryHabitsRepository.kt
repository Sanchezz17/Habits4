package com.example.habits4.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habits4.ui.home.habits.Habit


class InMemoryHabitsRepository {
    companion object {
        private val mutableHabits: MutableLiveData<MutableMap<Int, Habit>> = MutableLiveData(
            mutableMapOf()
        )

        val habits: LiveData<MutableMap<Int, Habit>> = mutableHabits

        fun addHabit(habit: Habit) {
            habit.id = mutableHabits.value!!.size
            mutableHabits.value?.set(habit.id, habit)
        }

        fun updateHabit(habitId: Int, newHabit: Habit) {
            mutableHabits.value?.set(habitId, newHabit)
        }

        fun loadHabit(habitId: Int): Habit? {
            return mutableHabits.value?.get(habitId)
        }
    }
}