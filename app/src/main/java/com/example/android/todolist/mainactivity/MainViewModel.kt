package com.example.android.todolist.mainactivity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.android.todolist.database.AppDatabase
import com.example.android.todolist.database.TaskEntry

/**
 * Created by Raed Saeed on 8/3/2019.
 */
class MainViewModel(application: Application): AndroidViewModel(application) {
    val tasks : LiveData<List<TaskEntry>>

    init {
        val appDatabase : AppDatabase? = AppDatabase.getInstance(application.applicationContext)
        tasks = appDatabase?.taskDao()?.loadAllTasks()!!
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}