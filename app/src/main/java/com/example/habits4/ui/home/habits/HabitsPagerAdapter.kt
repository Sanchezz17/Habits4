package com.example.habits4.ui.home.habits

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits4.R


class HabitsPagerAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HabitsFragment.newInstance(fragment.getString(R.string.harmful))
        else -> HabitsFragment.newInstance(fragment.getString(R.string.useful))
    }
}