package com.example.habits4.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}