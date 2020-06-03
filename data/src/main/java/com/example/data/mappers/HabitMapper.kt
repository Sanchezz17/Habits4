package com.example.data.mappers

import com.example.data.models.Habit
import com.example.domain.entities.HabitEntity


internal fun HabitEntity.toModel() : Habit {
    val habit = Habit(
        title, description, priority, type, count, frequency, color, date
    )
    habit.uid = uid;
    return habit
}

internal fun Habit.toEntity() : HabitEntity {
    val habitEntity = HabitEntity(
        title, description, priority, type, count, frequency, color, date
    )
    habitEntity.uid = uid;
    return habitEntity
}
