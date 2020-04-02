package com.example.habits4.ui.home.habits.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habits4.R
import com.example.habits4.infrastructure.OnItemClickListener
import com.example.habits4.infrastructure.addOnItemClickListener
import com.example.habits4.ui.home.HomeFragmentDirections
import com.example.habits4.ui.home.habits.Habit
import com.example.habits4.ui.home.habits.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.*


class HabitsFragment : Fragment() {
    companion object {

        fun newInstance(habitsType: String): HabitsFragment {
            val fragment = HabitsFragment()
            fragment.habitsType = habitsType
            return fragment
        }
    }

    private val viewModel: HabitsViewModel by activityViewModels()

    var habitsType: String = ""
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            initializeRecyclerView(habits)
        })
    }

    private fun initializeRecyclerView(habits: List<Habit>) {
        habitsRecyclerView.apply {
            val filteredHabits = habits.filter { it.habitType == habitsType }
            adapter = HabitsRecycleViewAdapter(filteredHabits)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
            addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    val habit = filteredHabits[position]
                    val action = HomeFragmentDirections.actionHabitsFragmentToEditHabitFragment(
                        habit.id
                    )
                    navController.navigate(action)
                }
            })
        }
    }
}
