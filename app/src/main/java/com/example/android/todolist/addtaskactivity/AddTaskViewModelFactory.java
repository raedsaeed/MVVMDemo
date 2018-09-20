package com.example.android.todolist.addtaskactivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.todolist.database.AppDatabase;

/**
 * Created by raed on 9/20/18.
 */

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase database;
    private final int taskId;

    public AddTaskViewModelFactory (AppDatabase database, int taskId) {
        this.database = database;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(database, taskId);
    }
}
