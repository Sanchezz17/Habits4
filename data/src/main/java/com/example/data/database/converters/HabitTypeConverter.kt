package com.example.data.database.converters

import androidx.room.TypeConverter
import com.example.domain.enums.HabitType


class HabitTypeConverter {
    @TypeConverter
    fun fromHabitType(habitType: HabitType?): Int? {
        return habitType?.value
    }

    @TypeConverter
    fun intToHabitType(value: Int?): HabitType? {
        return HabitType.getByValue(value)
    }
}