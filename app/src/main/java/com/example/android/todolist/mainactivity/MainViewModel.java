package com.example.android.todolist.mainactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

/**
 * Created by raed on 9/20/18.
 */

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";

    private LiveData<List<TaskEntry>> tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        Log.d(TAG, "MainViewModel: get data from database");
        tasks = appDatabase.taskDao().loadAllTasks();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }
}
