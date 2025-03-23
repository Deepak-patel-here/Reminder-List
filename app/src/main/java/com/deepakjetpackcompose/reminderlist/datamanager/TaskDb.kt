package com.deepakjetpackcompose.reminderlist.datamanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1)
abstract class TaskDb :RoomDatabase(){

    abstract fun getDao():TaskDao
    companion object{
        @Volatile
        private var INSTANCE:TaskDb?=null
         fun getDatabase(context: Context):TaskDb{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDb::class.java,
                    "task_db"
                ).build()
                INSTANCE=instance
                instance
            }
         }
    }
}