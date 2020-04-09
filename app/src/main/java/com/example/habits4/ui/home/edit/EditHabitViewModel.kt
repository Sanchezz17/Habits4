package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.habits4.App
import com.example.habits4.database.Habit
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class EditHabitViewModel(private val habitId: Int) : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()

    val habit: LiveData<Habit?> = App.database.habitDao().getById(habitId)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job + CoroutineExceptionHandler { _, e -> throw e }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun saveHabit(habit: Habit) = launch {
        val habitDao = App.database.habitDao()
        if (habitId == Habit.INVALID_ID) {
            withContext(Dispatchers.IO) { habitDao.insert(habit) }
        } else {
            habit.id = habitId
            withContext(Dispatchers.IO) { habitDao.update(habit) }
        }
    }

}