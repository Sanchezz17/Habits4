package com.example.habits4

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.habits4.ui.home.edit.EditHabitFragmentCallback
import com.example.habits4.ui.home.habits.Habit
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), EditHabitFragmentCallback {
    companion object {
        const val HABITS_KEY: String = "habits"
    }

    var habits = arrayListOf<Habit>()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.navHostFragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navHome, R.id.navAbout), mainDrawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(HABITS_KEY, habits)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        habits = savedInstanceState.getParcelableArrayList<Habit>(HABITS_KEY) ?: arrayListOf()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onHabitEdited(habitIndex: Int, habit: Habit) {
        Log.d("kek", "added privichka")
        if (habitIndex == -1) {
            habits.add(habit)
        } else {
            habits[habitIndex] = habit
        }
        //homeViewPager.adapter?.notifyDataSetChanged()
    }
}
