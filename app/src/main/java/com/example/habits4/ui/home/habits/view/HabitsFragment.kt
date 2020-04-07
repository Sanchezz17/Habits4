package com.example.habits4.ui.home.habits.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habits4.App
import com.example.habits4.R
import com.example.habits4.ui.home.HomeFragmentDirections
import com.example.habits4.database.Habit
import com.example.habits4.ui.home.habits.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.*


class HabitsFragment : Fragment() {
    companion object {
        private const val ARGS_HABIT_TYPE = "HABIT_TYPE"

        fun newInstance(habitsType: String): HabitsFragment {
            val fragment = HabitsFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_HABIT_TYPE, habitsType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: HabitsViewModel by activityViewModels()

    var habitsType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            habitsType = it.getString(ARGS_HABIT_TYPE, "")
        }

        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            initializeRecyclerView(habits)
        })
    }

    private fun initializeRecyclerView(habits: List<Habit>) {
        habitsRecyclerView.apply {
            val filteredHabits = habits.filter { it.habitType == habitsType }
            adapter = HabitsRecycleViewAdapter(filteredHabits,
                { habit ->
                    val action = HomeFragmentDirections.actionHabitsFragmentToEditHabitFragment(
                        habit.id ?: Habit.INVALID_ID
                    )
                    findNavController().navigate(action)
                },
                { view, habit ->
                    val popupMenu = PopupMenu(context, view)
                    popupMenu.inflate(R.menu.habit_long_click_menu)
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.delete_habit -> App.database.habitDao().delete(habit)
                        }
                        true
                    }
                    popupMenu.show()
                    true
                })
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }
    }
}
