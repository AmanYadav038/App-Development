package com.example.todolist

object DatabaseContract {
    object TaskEntry{
        const val TABLE_NAME="tasks"
        const val COLUMN_ID="id"
        const val COLUMN_STATUS="status"
        const val COLUMN_TASK="task"
    }
}