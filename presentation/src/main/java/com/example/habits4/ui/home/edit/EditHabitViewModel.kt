package com.example.habits4.ui.home.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.HabitEntity
import com.example.domain.useCases.AddOrUpdateHabitUseCase
import com.example.domain.useCases.GetHabitByUidUseCase
import kotlinx.coroutines.*
import javax.inject.Inject


class EditHabitViewModel (habitUid: String?) : ViewModel() {

    @Inject
    lateinit var getHabitByUidUseCase: GetHabitByUidUseCase

    @Inject
    lateinit var addOrUpdateHabitUseCase: AddOrUpdateHabitUseCase

    val habit: LiveData<HabitEntity?> = getHabitByUidUseCase.getHabitByUid(habitUid).asLiveData()

    fun saveHabit(habitEntity: HabitEntity) = viewModelScope.launch(Dispatchers.Default) {
        addOrUpdateHabitUseCase.addOrUpdateHabit(habitEntity)
    }
}