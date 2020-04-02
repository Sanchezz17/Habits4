package com.example.habits4.ui.home.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habits4.repositories.InMemoryHabitsRepository


class HabitsViewModel : ViewModel() {
    val nameFilterSubstring: MutableLiveData<String>
        get() = MutableLiveData()

    val habits: LiveData<List<Habit>>
        get() = MutableLiveData(
            InMemoryHabitsRepository.habits.value
                ?.filter { nameFilterSubstring.value.isNullOrEmpty() || nameFilterSubstring.value!! in it.name }
                ?: listOf()
        )
}