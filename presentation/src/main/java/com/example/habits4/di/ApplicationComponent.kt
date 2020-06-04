package com.example.habits4.di

import com.example.data.di.DataModule
import com.example.habits4.ui.home.edit.EditHabitViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class])
interface ApplicationComponent {
    fun inject(viewModel: EditHabitViewModel)
}