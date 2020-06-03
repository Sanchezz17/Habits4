package com.example.habits4.ui.home.habits.view

import android.os.Bundle
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
import com.example.habits4.R
import com.example.habits4.ui.home.HomeFragmentDirections
import com.example.data.model.Habit
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

        initializeRecyclerView()

        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            (habitsRecyclerView.adapter as HabitsRecycleViewAdapter)
                .setHabits(habits.filter { it.type.title == habitsType })
        })
    }

    private fun initializeRecyclerView() {
        habitsRecyclerView.apply {
            val filteredHabits =
                viewModel.habits.value?.filter { it.type.title == habitsType } ?: listOf()
            adapter = HabitsRecycleViewAdapter(filteredHabits, ::onHabitClick, ::onLongHabitClick)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
        }
    }

    private fun onHabitClick(habit: Habit) {
        val action = HomeFragmentDirections.actionHabitsFragmentToEditHabitFragment(habit.uid)
        findNavController().navigate(action)
    }

    private fun onLongHabitClick(view: View, habit: Habit): Boolean {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.habit_long_click_menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_habit -> viewModel.deleteHabit(habit)
            }
            true
        }
        popupMenu.show()
        return true
    }
}
