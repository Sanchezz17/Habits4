package com.example.data.models

import android.graphics.Color
import androidx.room.*
import com.example.data.database.converters.HabitPriorityConverter
import com.example.data.database.converters.HabitTypeConverter
import com.example.domain.entities.enums.HabitPriority
import com.example.domain.entities.enums.HabitType
import androidx.annotation.NonNull


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
    @PrimaryKey
    @NonNull
    var uid: String? = null
}