package com.example.habits4.ui.home.habits

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.habits4.MainActivity
import com.example.habits4.R
import com.example.habits4.infrastructure.OnItemClickListener
import com.example.habits4.infrastructure.addOnItemClickListener
import com.example.habits4.ui.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_habits_list.*


class HabitsFragment : Fragment() {
    companion object {

        fun newInstance(habitsType: String): HabitsFragment {
            val fragment = HabitsFragment()
            fragment.habitsType = habitsType
            return fragment
        }
    }

    var habitsType: String = ""
    private lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        recyclerView = habitsRecyclerView.apply {
            val allHabits = (activity as MainActivity).habits
            val filteredHabits = allHabits
                .filter { it.habitType == habitsType }
            adapter = HabitsRecycleViewAdapter(filteredHabits)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    val allHabits = (activity as MainActivity).habits
                    val filteredHabits = allHabits
                        .filter { it.habitType == habitsType }
                    val habit = filteredHabits[position]
                    val positionInWholeList = allHabits.indexOf(habit)
                    val action = HomeFragmentDirections.actionHabitsFragmentToEditHabitFragment(
                        positionInWholeList, habit
                    )
                    navController.navigate(action)
                }
            })
        }
    }
}
