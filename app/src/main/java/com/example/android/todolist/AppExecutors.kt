package com.example.android.todolist

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by raed on 9/15/18.
 */

class AppExecutors(val diskIO: Executor, val networkIO: Executor, val mainThread: Executor) {

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        private val LOCK = Any()
        private var mInstance: AppExecutors? = null

        val instance: AppExecutors?
            get() {
                if (mInstance == null) {
                    synchronized(LOCK) {
                        mInstance = AppExecutors(Executors.newSingleThreadExecutor(),
                                Executors.newFixedThreadPool(3),
                                MainThreadExecutor())
                    }
                }
                return mInstance
            }
    }
}
