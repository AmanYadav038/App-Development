package com.example.findrecipe

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities =[recipe::class], exportSchema = false, version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getDao():Dao
}