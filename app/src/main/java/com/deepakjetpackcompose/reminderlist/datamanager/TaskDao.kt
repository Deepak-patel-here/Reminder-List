package com.deepakjetpackcompose.reminderlist.datamanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTask():LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE title LIKE '%' || :query || '%'")
    fun searchTasks(query: String): LiveData<List<Task>>
}