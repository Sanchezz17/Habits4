package com.example.habits4.model

import android.graphics.Color
import androidx.room.*
import com.example.habits4.database.converters.HabitPriorityConverter
import com.example.habits4.database.converters.HabitTypeConverter
import com.example.habits4.model.enums.HabitPriority
import com.example.habits4.model.enums.HabitType


@Entity
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class)
data class Habit(
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: HabitPriority,
    @ColumnInfo val type: HabitType,
    @ColumnInfo val count: Int,
    @ColumnInfo val frequency: Int,
    @ColumnInfo(defaultValue = Color.BLACK.toString()) val color: Int,
    @ColumnInfo val date: Long
) {
    @PrimaryKey(autoGenerate = true) var uid: Int? = null

    companion object {
        @Ignore val INVALID_ID: Int = -1
    }
}