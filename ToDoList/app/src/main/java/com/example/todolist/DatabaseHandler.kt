package com.example.todolist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION

class DatabaseHandler(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME="tasks.db"
        private const val DATABASE_VERSION=1

        private const val SQL_CREATE_ENTRIES=
            "CREATE TABLE ${DatabaseContract.TaskEntry.TABLE_NAME}(" +
                    "${DatabaseContract.TaskEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    "${DatabaseContract.TaskEntry.COLUMN_STATUS} INTEGER," +
                    "${DatabaseContract.TaskEntry.COLUMN_TASK} TEXT)"
        private const val SQL_DELETE_ENTRIES=
            "DROP TABLE IF EXISTS ${DatabaseContract.TaskEntry.TABLE_NAME}"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db);
    }


}