package com.example.habits4.ui.home.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.habits4.R
import com.example.habits4.ui.home.HomeFragmentDirections
import com.example.habits4.ui.home.habits.HabitsViewModel
import kotlinx.android.synthetic.main.bottom_sheet.*


class BottomSheetFragment : Fragment() {
    private val viewModel: HabitsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addHabitButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHabitsFragmentToEditHabitFragment()
            findNavController().navigate(action)
        }

        nameFilter.addTextChangedListener { nameFilterSubstring ->
            viewModel.nameFilterSubstring.postValue(nameFilterSubstring.toString())
        }
    }
}