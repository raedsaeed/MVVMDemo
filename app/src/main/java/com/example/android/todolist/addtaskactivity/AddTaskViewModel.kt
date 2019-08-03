package com.example.android.todolist.addtaskactivity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.android.todolist.database.AppDatabase
import com.example.android.todolist.database.TaskEntry

/**
 * Created by Raed Saeed on 8/3/2019.
 */
class AddTaskViewModel(appDatabase: AppDatabase, taskId: Int) : ViewModel() {
    val task: LiveData<TaskEntry> = appDatabase.taskDao().loadTaskById(taskId)
}