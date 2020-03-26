package com.example.habits4.infrastructure

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

fun getHueGradient(): GradientDrawable {
    val colors = mutableListOf<Int>()
    for (hue in 0..360) {
        colors.add(Color.HSVToColor(floatArrayOf(hue.toFloat(), 1f, 1f)))
    }
    val gradient =
        GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors.toIntArray())
    gradient.cornerRadius = 0f;
    return gradient
}
