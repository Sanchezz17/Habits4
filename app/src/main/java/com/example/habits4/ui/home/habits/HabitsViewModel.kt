package com.example.habits4.ui.home.habits

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.habits4.App
import com.example.habits4.model.Habit
import java.util.*


class HabitsViewModel : ViewModel() {
    private val allHabits: LiveData<List<Habit>> = App.database.habitDao().getAll()

    val nameFilterSubstring: MutableLiveData<String> = MutableLiveData()
    val habits: MediatorLiveData<List<Habit>> = MediatorLiveData()

    init {
        habits.addSource(allHabits, Observer { newHabits ->
            habits.value = newHabits.filter { filterHabitsByName(it) }
        })
        habits.addSource(nameFilterSubstring, Observer { newNameFilterSubstring ->
            habits.value = allHabits.value?.filter {
                filterHabitsByName(it, newNameFilterSubstring)
            } ?: listOf()
        })
    }

    private fun filterHabitsByName(
        habit: Habit,
        newNameFilterSubstring: String? = nameFilterSubstring.value
    ): Boolean {
        return newNameFilterSubstring.isNullOrEmpty() ||
                newNameFilterSubstring.toLowerCase(Locale.ROOT) in habit.title.toLowerCase(Locale.ROOT)
    }

    fun sortByDateAsc() {
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortByDateDesc() {
        habits.value = habits.value?.sortedByDescending { it.date }
    }

    fun deleteHabit(habit: Habit) {
        App.database.habitDao().delete(habit)
    }
}