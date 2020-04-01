package com.example.habits4.ui.home.habits

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Habit(
    var id: Int,
    val name: String,
    val description: String,
    val priority: String,
    val habitType: String,
    val runAmount: Int,
    val periodicity: Int,
    val color: Int = Color.BLACK
) : Parcelable