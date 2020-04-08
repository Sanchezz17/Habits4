package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.habits4.App
import com.example.habits4.database.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditHabitViewModel(private val habitId: Int) : ViewModel() {
    val habit: LiveData<Habit?> = App.database.habitDao().getById(habitId)

    fun postSaveHabit(habit: Habit) {
        GlobalScope.launch(Dispatchers.Default) {
            saveHabit(habit)
        }
    }

    private suspend fun saveHabit(habit: Habit) {
        val habitDao = App.database.habitDao()
        if (habitId == Habit.INVALID_ID) {
            withContext(Dispatchers.IO) { habitDao.insert(habit) }
        } else {
            habit.id = habitId
            withContext(Dispatchers.IO) { habitDao.update(habit) }
        }
    }
}