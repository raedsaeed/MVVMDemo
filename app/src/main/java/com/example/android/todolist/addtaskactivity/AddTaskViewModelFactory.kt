package com.example.android.todolist.addtaskactivity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.android.todolist.database.AppDatabase

/**
 * Created by Raed Saeed on 8/3/2019.
 */
class AddTaskViewModelFactory(private val appDatabase: AppDatabase, private val taskId : Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddTaskViewModel(appDatabase, taskId) as T
    }
}