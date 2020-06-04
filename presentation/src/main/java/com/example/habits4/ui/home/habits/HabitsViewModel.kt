package com.example.habits4.ui.home.habits

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.domain.entities.HabitEntity
import com.example.domain.useCases.DeleteHabitUseCase
import com.example.domain.useCases.GetAllHabitsUseCase
import com.example.domain.useCases.InitializeHabitsInDbUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class HabitsViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val initializeHabitsInDbUseCase: InitializeHabitsInDbUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
): ViewModel() {

    private val allHabits: LiveData<List<HabitEntity>> = getAllHabitsUseCase.getAllHabits().asLiveData()

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
            initializeHabitsInDbUseCase.initializeHabitsInDB()
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
        deleteHabitUseCase.deleteHabit(habitEntity)
    }
}