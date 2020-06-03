package com.example.habits4.ui.home.edit

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.habits4.infrastructure.getHueGradient
import com.example.data.model.Habit
import com.example.habits4.R
import com.example.habits4.infrastructure.hideKeyboard
import com.example.domain.enums.HabitPriority
import com.example.domain.enums.HabitType
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_edit_habit.*
import java.util.*


class EditHabitFragment : Fragment() {
    private val colorRectangles = mutableListOf<MaterialButton>()
    private var habitUid: String? = null
    private var habitColor: Int = Color.BLACK
    private var habitRect: MaterialButton? = null

    private lateinit var navController: NavController
    private lateinit var colorScrollBitmap: Bitmap
    private lateinit var viewModel: EditHabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitUid = EditHabitFragmentArgs.fromBundle(it).habitUid
        }
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditHabitViewModel(habitUid) as T
            }
        }).get(EditHabitViewModel::class.java)
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

        viewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
            habit?.let { restoreHabitState(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_habit_menu, menu)
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

    private fun initializeColorRectangles() {
        val rectSize = resources.getDimension(R.dimen.rect_size).toInt()
        val rectMargin = resources.getDimension(R.dimen.rect_margin).toInt()
        val layoutParams = LinearLayout.LayoutParams(rectSize, rectSize)
        layoutParams.setMargins(rectMargin, rectMargin, rectMargin, rectMargin)
        for (i in 1..16) {
            context?.let {
                val rect = MaterialButton(it)
                rect.layoutParams = layoutParams
                rect.setStrokeColorResource(R.color.colorPrimaryDark)
                rect.strokeWidth = resources.getDimension(R.dimen.rect_stroke_width).toInt()
                colorScrollLayout.addView(rect)
                colorRectangles.add(rect)
            }
        }

        addColorToRectangles(colorRectangles, rectSize, rectMargin)
    }

    private fun addColorToRectangles(
        colorRectangles: MutableList<MaterialButton>,
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
                    habitRect!!.icon = null
                }
                habitColor = centerColor
                setCheckedIconToRect(rect)
                habitRect = rect
            }
        }
    }

    private fun restoreHabitState(habit: Habit) {
        newTitle.setText(habit.title)
        newDescription.setText(habit.description)
        newPriority.setSelection(habit.priority.value)

        val typeViews = arrayListOf<View>()
        newType.findViewsWithText(typeViews, habit.type.title, View.FIND_VIEWS_WITH_TEXT)
        if (typeViews.size > 0) {
            newType.check(typeViews[0].id)
        }

        newCount.setText(habit.count.toString())
        newFrequency.setText(habit.frequency.toString())

        habitColor = habit.color
        val rectSize = resources.getDimension(R.dimen.rect_size).toInt()
        habitRect = colorRectangles.find {
            it.background.toBitmap(rectSize, rectSize)
                .getPixel(rectSize / 2, rectSize / 2) == habitColor
        }
        habitRect?.let { setCheckedIconToRect(it) }
    }

    private fun setCheckedIconToRect(rect: MaterialButton) {
        rect.setIconResource(R.drawable.done)
        rect.iconSize = resources.getDimension(R.dimen.rect_size).toInt()
        rect.iconGravity = MaterialButton.ICON_GRAVITY_TEXT_END
        rect.iconPadding = resources.getDimension(R.dimen.rect_icon_padding).toInt()
    }

    private fun isAllFieldsFilled(): Boolean {
        return newTitle.text.isNotEmpty()
                && newDescription.text.isNotEmpty()
                && newType.checkedRadioButtonId != -1
                && newCount.text.isNotEmpty()
                && newFrequency.text.isNotEmpty()
                && habitRect != null
    }

    private fun saveHabit() {
        if (!isAllFieldsFilled()) {
            Toast.makeText(
                context,
                getString(R.string.edit_habit_not_filled_toast),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val resultHabit = Habit(
            newTitle.text.toString(),
            newDescription.text.toString(),
            HabitPriority.getByTitle(newPriority.selectedItem.toString()),
            HabitType.getByTitle(
                newType.findViewById<RadioButton>(newType.checkedRadioButtonId).text.toString()
            ),
            newCount.text.toString().toInt(),
            newFrequency.text.toString().toInt(),
            habitColor,
            Date().time
        )

        resultHabit.uid = habitUid

        viewModel.saveHabit(resultHabit)

        hideKeyboard()

        navController.navigateUp()
    }
}
