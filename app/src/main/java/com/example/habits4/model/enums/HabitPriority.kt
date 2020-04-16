package com.example.habits4.model.enums

enum class HabitPriority(val value: Int, val title: String) {
    LOW(0, "Низкий"),
    MEDIUM(1, "Средний"),
    HIGH(2, "Высокий");

    companion object {
        fun getByTitle(title: String): HabitPriority {
            return values().first { it.title == title }
        }

        fun getByValue(value: Int?): HabitPriority {
            return HabitPriority.values().first { it.value == value }
        }
    }
}