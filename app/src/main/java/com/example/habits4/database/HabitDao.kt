package com.example.habits4.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits4.model.Habit


@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE uid=:id")
    fun getById(id: Int): LiveData<Habit?>

    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)
}