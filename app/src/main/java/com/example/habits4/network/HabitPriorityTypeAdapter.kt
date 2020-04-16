package com.example.habits4.network

import com.example.habits4.model.enums.HabitPriority
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class HabitPriorityTypeAdapter : TypeAdapter<HabitPriority>() {
    override fun write(writer: JsonWriter?, habitPriority: HabitPriority?) {
        writer?.value(habitPriority?.value)
    }

    override fun read(reader: JsonReader?): HabitPriority {
        return HabitPriority.getByValue(reader?.nextInt())
    }
}