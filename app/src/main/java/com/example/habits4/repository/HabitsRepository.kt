package com.example.habits4.repository

import com.example.habits4.database.HabitDao
import com.example.habits4.network.HabitApi

class HabitsRepository(val habitDao: HabitDao, val habitApi: HabitApi) {

}