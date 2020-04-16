package com.example.habits4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.habits4.R
import com.example.habits4.ui.home.habits.HabitsViewModel
import com.example.habits4.ui.home.habits.view.HabitsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private val viewModel: HabitsViewModel by activityViewModels()

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

        nameFilter.addTextChangedListener {
            viewModel.nameFilterSubstring.value = it.toString()
        }

        sortByDateAsc.setOnClickListener {
            viewModel.sortByDateAsc()
        }

        sortByDateDesc.setOnClickListener {
            viewModel.sortByDateDesc()
        }
    }
}
