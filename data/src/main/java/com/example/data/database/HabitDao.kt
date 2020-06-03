package com.example.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.models.Habit
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE uid=:uid")
    fun getByUid(uid: String?): Flow<Habit?>

    @Insert
    fun insert(habit: Habit)

    @Insert
    fun insertAll(habits: List<Habit>)

    @Update
    fun update(habit: Habit)

    @Update
    fun updateAll(habits: List<Habit>)

    @Delete
    fun delete(habit: Habit)

    @Query("DELETE FROM habit")
    fun clearTable()
}