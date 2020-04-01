package com.example.habits4.repositories

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habits4.ui.home.habits.Habit


class InMemoryHabitsRepository {
    companion object {
        private val mutableHabits: MutableMap<Int, Habit> = mutableMapOf(
            0 to Habit(0, "a", "a", "Низкий", "Вредная", 1, 1, Color.BLUE)
        )

        val habits: LiveData<List<Habit>>
            get() = MutableLiveData(mutableHabits.values.toList())

        fun addHabit(habit: Habit) {
            habit.id = mutableHabits.size
            mutableHabits[mutableHabits.size] = habit
        }

        fun updateHabit(habitId: Int, newHabit: Habit) {
            mutableHabits[habitId] = newHabit
        }

        fun loadHabit(habitId: Int): Habit? {
            return mutableHabits[habitId]
        }
    }
}