package com.example.habits4.database

import android.graphics.Color
import androidx.room.*
import java.util.*


@Entity
@TypeConverters(DateTypeConverter::class)
data class Habit(
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: String,
    @ColumnInfo val habitType: String,
    @ColumnInfo val runAmount: Int,
    @ColumnInfo val periodicity: Int,
    @ColumnInfo(defaultValue = Color.BLACK.toString()) val color: Int,
    @ColumnInfo val editDate: Date
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null

    companion object {
        @Ignore val INVALID_ID: Int = -1
    }
}