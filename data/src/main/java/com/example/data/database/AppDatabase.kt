package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.Habit


@Database(entities = [Habit::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}