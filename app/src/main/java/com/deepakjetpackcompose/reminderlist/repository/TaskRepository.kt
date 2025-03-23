package com.deepakjetpackcompose.reminderlist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.deepakjetpackcompose.reminderlist.datamanager.Task
import com.deepakjetpackcompose.reminderlist.datamanager.TaskDao
import com.deepakjetpackcompose.reminderlist.datamanager.TaskDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(application: Application) {
    val allTask:LiveData<List<Task>>
    private val taskDao: TaskDao
    init {
        taskDao=TaskDb.getDatabase(application).getDao()
        allTask=taskDao.getAllTask()
    }


    //insert
    suspend fun insertTask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.insert(task)
        }
    }

    //delete
    suspend fun deleteTask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.delete(task)
        }
    }

    //update
    suspend fun updateTask(task: Task){
        withContext(Dispatchers.IO){
            taskDao.update(task)
        }
    }

    //search

    fun search(query:String): LiveData<List<Task>>{
        return taskDao.searchTasks(query)
    }


}