package com.example.habits4.ui.home.habits.view

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habits4.R
import com.example.habits4.ui.home.habits.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_habits.view.*


class HabitsRecycleViewAdapter(
    private val habits: List<Habit>
) : RecyclerView.Adapter<HabitsRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit) {
            containerView.habitName.text = habit.name
            containerView.habitDescription.text = habit.description
            containerView.habitPriority.text =
                containerView.context.getString(R.string.priority_placeholder, habit.priority)
            containerView.habitRunAmount.text =
                containerView.context.resources.getQuantityString(
                    R.plurals.run_amount_placeholder,
                    habit.runAmount,
                    habit.runAmount
                )
            containerView.habitPeriodicity.text =
                containerView.context.resources.getQuantityString(
                    R.plurals.periodicity_placeholder,
                    habit.periodicity,
                    habit.periodicity
                )
            containerView.habitColor.setBackgroundColor(habit.color)
            containerView.habitColorRGB.text =
                containerView.context.getString(
                    R.string.rgb_placeholder,
                    Color.red(habit.color),
                    Color.green(habit.color),
                    Color.blue(habit.color)
                )
            val hsv = floatArrayOf(0f, 0f, 0f)
            Color.colorToHSV(habit.color, hsv)
            containerView.habitColorHSV.text =
                containerView.context.getString(
                    R.string.hsv_placeholder,
                    hsv[0].toInt(),
                    hsv[1].toInt(),
                    hsv[2].toInt()
                )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_habits, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position])
    }
}
