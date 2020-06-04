package com.example.habits4.ui.home.habits.view

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habits4.R
import com.example.domain.entities.HabitEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_habits.view.*
import java.text.SimpleDateFormat
import java.util.*


class HabitsRecycleViewAdapter(
    private var habits: List<HabitEntity>, private val onItemClickListener: (HabitEntity) -> Unit,
    private val onLongItemClickListener: (View, HabitEntity) -> Boolean
) : RecyclerView.Adapter<HabitsRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val simpleDateFormat = SimpleDateFormat("HH:mm  dd.MM.yyyy ", Locale.ROOT)

        fun bind(
            habitEntity: HabitEntity, onItemClickListener: (HabitEntity) -> Unit,
            onLongItemClickListener: (View, HabitEntity) -> Boolean
        ) {
            containerView.habitTitle.text = habitEntity.title
            containerView.habitDescription.text = habitEntity.description
            containerView.habitPriority.text =
                containerView.context.getString(R.string.priority_placeholder, habitEntity.priority.title)
            containerView.habitCount.text =
                containerView.context.resources.getQuantityString(
                    R.plurals.run_amount_placeholder,
                    habitEntity.count,
                    habitEntity.count
                )
            containerView.habitFrequency.text =
                containerView.context.resources.getQuantityString(
                    R.plurals.periodicity_placeholder,
                    habitEntity.frequency,
                    habitEntity.frequency
                )
            containerView.habitEditDate.text = simpleDateFormat.format(Date(habitEntity.date))
            containerView.habitColor.setBackgroundColor(habitEntity.color)
            containerView.habitColorRGB.text =
                containerView.context.getString(
                    R.string.rgb_placeholder,
                    Color.red(habitEntity.color),
                    Color.green(habitEntity.color),
                    Color.blue(habitEntity.color)
                )
            val hsv = floatArrayOf(0f, 0f, 0f)
            Color.colorToHSV(habitEntity.color, hsv)
            containerView.habitColorHSV.text =
                containerView.context.getString(
                    R.string.hsv_placeholder,
                    hsv[0].toInt(),
                    hsv[1].toInt(),
                    hsv[2].toInt()
                )
            containerView.setOnClickListener { onItemClickListener(habitEntity) }
            containerView.setOnLongClickListener { onLongItemClickListener(it, habitEntity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_habits, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position], onItemClickListener, onLongItemClickListener)
    }

    fun setHabits(newHabits: List<HabitEntity>) {
        habits = newHabits
        notifyDataSetChanged()
    }
}
