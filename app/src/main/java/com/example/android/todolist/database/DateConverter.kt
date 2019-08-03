package com.example.android.todolist.database

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Raed Saeed on 8/3/2019.
 */
class DateConverter {
    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return if (timeStamp == null) null else Date(timeStamp)
    }

    @TypeConverter
    fun toLong(timeStamp: Date?): Long? {
        return timeStamp?.time
    }
}