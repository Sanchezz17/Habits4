package com.example.habits4.network

import com.example.habits4.model.enums.HabitType
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class HabitTypeTypeAdapter : TypeAdapter<HabitType>() {
    override fun write(writer: JsonWriter?, habitType: HabitType?) {
        writer?.value(habitType?.value)
    }

    override fun read(reader: JsonReader?): HabitType {
        return HabitType.getByValue(reader?.nextInt())
    }
}