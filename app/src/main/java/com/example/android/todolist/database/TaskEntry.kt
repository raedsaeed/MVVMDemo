package com.example.android.todolist.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

import java.util.Date

// COMPLETED (2) Annotate the class with Entity. Use "task" for the table name
@Entity(tableName = "task")
class TaskEntry {

    // COMPLETED (3) Annotate the id as PrimaryKey. Set autoGenerate to true.
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var description: String? = null
    var priority: Int = 0
    @ColumnInfo(name = "updated_at")
    var updatedAt: Date? = null

    // COMPLETED (4) Use the Ignore annotation so Room knows that it has to use the other constructor instead
    @Ignore
    constructor(description: String, priority: Int, updatedAt: Date) {
        this.description = description
        this.priority = priority
        this.updatedAt = updatedAt
    }

    constructor(id: Int, description: String, priority: Int, updatedAt: Date) {
        this.id = id
        this.description = description
        this.priority = priority
        this.updatedAt = updatedAt
    }
}
