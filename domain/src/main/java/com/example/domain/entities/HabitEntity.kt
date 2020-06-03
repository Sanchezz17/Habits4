package com.example.domain.entities

import com.example.domain.entities.enums.HabitPriority
import com.example.domain.entities.enums.HabitType


data class HabitEntity(
    val title: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val count: Int,
    val frequency: Int,
    val color: Int,
    val date: Long
) {
    var uid: String? = null
}