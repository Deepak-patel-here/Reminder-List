package com.deepakjetpackcompose.reminderlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.deepakjetpackcompose.reminderlist.datamanager.Task
import com.deepakjetpackcompose.reminderlist.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository = TaskRepository(application)
    val allTask: LiveData<List<Task>> = taskRepository.allTask

    private val _searchResult = MutableLiveData<List<Task>>()
    val searchResult: LiveData<List<Task>> get() = _searchResult

    // Insert task
    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    // Delete task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    // Update task correctly
    fun updateTask(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy()
            taskRepository.updateTask(updatedTask)
        }
    }

    // Search task
    fun searchTask(query: String) {
        viewModelScope.launch {
            val searchResults = taskRepository.search(query)
            _searchResult.postValue(searchResults.value?.map { it.copy() } ?: emptyList())
        }
    }
}
