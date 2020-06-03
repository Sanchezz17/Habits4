package com.example.habits4.ui.home.habits

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.habits4.App
import com.example.data.models.Habit
import com.example.data.repositories.HabitsRepositoryImpl
import com.example.domain.entities.HabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class HabitsViewModel(private val habitsRepository: HabitsRepositoryImpl = App.habitsRepository) : ViewModel() {
    private val allHabits: LiveData<List<HabitEntity>> = habitsRepository.getAllHabits().asLiveData()

    val nameFilterSubstring: MutableLiveData<String> = MutableLiveData()
    val habits: MediatorLiveData<List<HabitEntity>> = MediatorLiveData()

    init {
        habits.addSource(allHabits, Observer { newHabits ->
            habits.value = newHabits.filter { filterHabitsByName(it) }
        })
        habits.addSource(nameFilterSubstring, Observer { newNameFilterSubstring ->
            habits.value = allHabits.value?.filter {
                filterHabitsByName(it, newNameFilterSubstring)
            } ?: listOf()
        })

        viewModelScope.launch(Dispatchers.Default) {
            habitsRepository.initializeHabitsInDB()
        }
    }

    private fun filterHabitsByName(
        habit: HabitEntity,
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

    fun deleteHabit(habitEntity: HabitEntity) = viewModelScope.launch(Dispatchers.Default) {
        habitsRepository.deleteHabit(habitEntity)
    }
}