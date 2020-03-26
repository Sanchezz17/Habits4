package com.example.habits4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habits4.R
import com.example.habits4.ui.home.habits.HabitsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewPager.adapter = HabitsPagerAdapter(this)
        TabLayoutMediator(homeTabs, homeViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.harmful_habits)
                else -> getString(R.string.useful_habits)
            }
        }.attach()

        addHabitButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHabitsFragmentToEditHabitFragment()
            findNavController().navigate(action)
        }
    }
}
