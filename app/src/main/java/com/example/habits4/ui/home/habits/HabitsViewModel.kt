package com.example.habits4.ui.home.habits

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.habits4.repositories.InMemoryHabitsRepository
import java.util.*


class HabitsViewModel : ViewModel() {
    val nameFilterSubstring: MutableLiveData<String> = MutableLiveData()

    val habits: MediatorLiveData<List<Habit>> = MediatorLiveData()

    init {
        habits.addSource(InMemoryHabitsRepository.habits, Observer { newHabits ->
            habits.value = newHabits.values.filter { filterHabitsByName(it) }
        })
        habits.addSource(nameFilterSubstring, Observer { newNameFilterSubstring ->
            habits.value = InMemoryHabitsRepository.habits.value?.values?.filter {
                filterHabitsByName(it, newNameFilterSubstring)
            }
        })
    }

    private fun filterHabitsByName(
        habit: Habit,
        newNameFilterSubstring: String? = nameFilterSubstring.value
    ): Boolean {
        return newNameFilterSubstring.isNullOrEmpty() ||
                newNameFilterSubstring.toLowerCase(Locale.ROOT) in habit.name.toLowerCase(Locale.ROOT)
    }

    fun sortByDateAsc() {
        habits.value = habits.value?.sortedBy { it.editDate }
    }

    fun sortByDateDesc() {
        habits.value = habits.value?.sortedByDescending { it.editDate }
    }
}