package com.example.habits4.model.enums

enum class HabitType(val value: Int, val title: String) {
    HARMFUL(0, "Вредная"),
    USEFUL(1, "Полезная");

    companion object {
        fun getByTitle(title: String): HabitType {
            return HabitType.values().first { it.title == title }
        }

        fun getByValue(value: Int?): HabitType {
            return HabitType.values().first { it.value == value }
        }
    }
}