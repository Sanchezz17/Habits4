package com.example.habits4.ui.home.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits4.repositories.InMemoryHabitsRepository


class HabitsViewModel(private val habitsFilter: (Habit) -> Boolean) : ViewModel() {
    val habits: LiveData<List<Habit>>
        get() = MutableLiveData(
            InMemoryHabitsRepository.habits.value?.filter(habitsFilter) ?: listOf()
        )
}