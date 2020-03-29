package com.example.habits4.ui.home.edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.habits4.infrastructure.getHueGradient
import com.example.habits4.ui.home.habits.Habit
import com.example.habits4.R
import kotlinx.android.synthetic.main.fragment_edit_habit.*


class EditHabitFragment : Fragment() {
    private val colorRectangles = mutableListOf<ImageButton>()
    private var habitIndex: Int = 0
    private var habitColor: Int = Color.BLACK
    private var habitRect: ImageButton? = null

    private var callback: EditHabitFragmentCallback? = null
    private lateinit var navController: NavController
    private lateinit var colorScrollBitmap: Bitmap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as EditHabitFragmentCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_edit_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        colorScrollLayout.background = getHueGradient()
        initializeColorRectangles()

        arguments?.let {
            val editHabitFragmentArgs = EditHabitFragmentArgs.fromBundle(it)
            habitIndex = editHabitFragmentArgs.habitIndex
            editHabitFragmentArgs.habit?.let {
                restoreHabitState(editHabitFragmentArgs.habit)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.edit_habit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
            }
            R.id.actionSave -> saveHabit()
        }
        return true
    }

    private fun restoreHabitState(habit: Habit) {
        newName.setText(habit.name)
        newDescription.setText(habit.description)

        val adapter = newPriority.adapter as ArrayAdapter<String>
        newPriority.setSelection(adapter.getPosition(habit.priority))

        val typeViews = arrayListOf<View>()
        newType.findViewsWithText(typeViews, habit.habitType, View.FIND_VIEWS_WITH_TEXT)
        if (typeViews.size > 0) {
            newType.check(typeViews[0].id)
        }

        newRunAmount.setText(habit.runAmount.toString())
        newPeriodicity.setText(habit.periodicity.toString())

        habitColor = habit.color
        val rectSize = resources.getDimension(R.dimen.rect_size).toInt()
        habitRect = colorRectangles.find {
            it.background.toBitmap(rectSize, rectSize).getPixel(0, 0) == habitColor
        }
        habitRect?.setImageResource(R.drawable.done)
    }

    private fun initializeColorRectangles() {
        val rectSize = resources.getDimension(R.dimen.rect_size).toInt()
        val rectMargin = resources.getDimension(R.dimen.rect_margin).toInt()
        val layoutParams = LinearLayout.LayoutParams(rectSize, rectSize)
        layoutParams.setMargins(rectMargin, rectMargin, rectMargin, rectMargin)
        for (i in 1..16) {
            val rect = ImageButton(context)
            rect.layoutParams = layoutParams
            rect.scaleType = ImageView.ScaleType.FIT_CENTER
            colorScrollLayout.addView(rect)
            colorRectangles.add(rect)
        }

        addColorToRectangles(colorRectangles, rectSize, rectMargin)
    }

    private fun addColorToRectangles(
        colorRectangles: MutableList<ImageButton>,
        rectSize: Int,
        rectMargin: Int
    ) {
        colorScroll.measure(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        colorScrollBitmap = colorScrollLayout.background.toBitmap(
            colorScroll.measuredWidth,
            colorScroll.measuredHeight
        )
        colorRectangles.forEachIndexed { index, rect ->
            val centerColor = colorScrollBitmap.getPixel(
                index * (rectSize + 2 * rectMargin) + rectSize / 2, rectMargin + rectSize / 2
            )
            rect.setBackgroundColor(centerColor)
            rect.setOnClickListener {
                if (habitRect != null) {
                    habitRect!!.setImageDrawable(null)
                }
                habitColor = centerColor
                habitRect = rect
                rect.setImageResource(R.drawable.done)
            }
        }
    }

    private fun isAllFieldsFilled(): Boolean {
        return newName.text.isNotEmpty()
                && newDescription.text.isNotEmpty()
                && newType.checkedRadioButtonId != -1
                && newRunAmount.text.isNotEmpty()
                && newPeriodicity.text.isNotEmpty()
                && habitRect != null
    }

    private fun saveHabit() {
        if (!isAllFieldsFilled()) {
            Toast.makeText(context, "Пожалуйста, заполните все поля", Toast.LENGTH_LONG)
                .show()
            return
        }

        val resultHabit = Habit(
            newName.text.toString(),
            newDescription.text.toString(),
            newPriority.selectedItem.toString(),
            newType.findViewById<RadioButton>(newType.checkedRadioButtonId)
                .text.toString(),
            newRunAmount.text.toString().toInt(),
            newPeriodicity.text.toString().toInt(),
            habitColor
        )

        callback?.onHabitEdited(habitIndex, resultHabit)
        navController.navigate(R.id.actionEditHabitFragmentToNavHome)
    }
}

interface EditHabitFragmentCallback {
    fun onHabitEdited(habitIndex: Int, habit: Habit)
}
