package com.example.android.todolist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

/**
 * Created by Raed Saeed on 8/3/2019.
 */
@Database(entities = [TaskEntry::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private val TAG = AppDatabase::class.simpleName
        private val LOCK = Any()
        private val DATABASE_NAME = "todolist"
        private var appDatabase: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase? {
            if (appDatabase == null) {
                synchronized(LOCK) {
                    appDatabase = Room.databaseBuilder(context,
                            AppDatabase::class.java,
                            DATABASE_NAME)
                            .build()
                }
            }
            return appDatabase
        }
    }
}