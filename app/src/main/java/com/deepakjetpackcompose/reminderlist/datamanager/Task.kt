package com.deepakjetpackcompose.reminderlist.datamanager

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id :Int=0,
    val title:String,
    val isChecked:Boolean=false,

)
