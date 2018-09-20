package com.example.android.todolist.addtaskactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

/**
 * Created by raed on 9/20/18.
 */

public class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntry> task;

    public AddTaskViewModel (AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask () {
        return task;
    }
}
