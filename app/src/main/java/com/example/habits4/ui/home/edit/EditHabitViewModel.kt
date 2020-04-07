package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.habits4.App
import com.example.habits4.database.Habit


class EditHabitViewModel(private val habitId: Int) : ViewModel() {
    val habit: LiveData<Habit?> = App.database.habitDao().getById(habitId)

    fun saveHabit(habit: Habit) {
        val habitDao = App.database.habitDao()
        if (habitId == Habit.INVALID_ID) {
            habitDao.insert(habit)
        } else {
            habit.id = habitId
            habitDao.update(habit)
        }
    }
}