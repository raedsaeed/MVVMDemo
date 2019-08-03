/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.todolist.addtaskactivity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.todolist.AppExecutors
import com.example.android.todolist.R
import com.example.android.todolist.database.AppDatabase
import com.example.android.todolist.database.TaskEntry
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*


class AddTaskActivity : AppCompatActivity() {
    // Fields for views

    private var mTaskId = DEFAULT_TASK_ID
    private lateinit var appDatabase: AppDatabase

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    val priorityFromViews: Int
        get() {
            var priority = 1
            val checkedId = radioGroup.checkedRadioButtonId
            when (checkedId) {
                R.id.radButton1 -> priority = PRIORITY_HIGH
                R.id.radButton2 -> priority = PRIORITY_MEDIUM
                R.id.radButton3 -> priority = PRIORITY_LOW
            }
            return priority
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        initViews()
        appDatabase = AppDatabase.getInstance(this)!!

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID)
        }

        val intent = intent
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            saveButton.setText(R.string.update_button)
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID)

                val factory = AddTaskViewModelFactory(appDatabase, mTaskId)
                val viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel::class.java)

                viewModel.task.observe(this, object : Observer<TaskEntry> {
                    override fun onChanged(t: TaskEntry?) {
                        viewModel.task.removeObserver(this)
                        populateUI(t)
                    }
                })

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId)
        super.onSaveInstanceState(outState)
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private fun initViews() {
        saveButton.setOnClickListener { onSaveButtonClicked() }
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private fun populateUI(task: TaskEntry?) {
        if (task == null) {
            return
        }
        // COMPLETED (8) use the variable task to populate the UI
        editTextTaskDescription.setText(task.description)
        setPriorityInViews(task.priority)
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    fun onSaveButtonClicked() {
        // Not yet implemented
        val description = editTextTaskDescription.text.toString()

        val periority = priorityFromViews

        val date = Date()

        val taskEntry = TaskEntry(description, periority, date)

        AppExecutors.instance?.diskIO?.execute {
            if (mTaskId == DEFAULT_TASK_ID) {
                // insert new task
                appDatabase.taskDao().insertTask(taskEntry)
            } else {
                //update task
                taskEntry.id = mTaskId
                appDatabase.taskDao().updateTask(taskEntry)
            }
        }
        finish()
    }

    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
    fun setPriorityInViews(priority: Int) {
        when (priority) {
            PRIORITY_HIGH -> radioGroup.check(R.id.radButton1)
            PRIORITY_MEDIUM -> radioGroup.check(R.id.radButton2)
            PRIORITY_LOW -> radioGroup.check(R.id.radButton3)
        }
    }

    companion object {

        // Extra for the task ID to be received in the intent
        val EXTRA_TASK_ID = "extraTaskId"
        // Extra for the task ID to be received after rotation
        val INSTANCE_TASK_ID = "instanceTaskId"
        // Constants for priority
        val PRIORITY_HIGH = 1
        val PRIORITY_MEDIUM = 2
        val PRIORITY_LOW = 3
        // Constant for default task id to be used when not in update mode
        private val DEFAULT_TASK_ID = -1
        // Constant for logging
        private val TAG = AddTaskActivity::class.java.simpleName
    }
}
