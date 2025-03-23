package com.deepakjetpackcompose.reminderlist.homescreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.deepakjetpackcompose.reminderlist.datamanager.Task
import com.deepakjetpackcompose.reminderlist.viewmodel.TaskViewModel

@Composable
fun ListOfTask(tasks:List<Task>,viewModel: TaskViewModel, modifier: Modifier = Modifier) {

    LazyColumn {
        items(tasks, key = {it.id}){
            item->
            CardTemplate(task = item, onDelete = {viewModel.deleteTask(item)}, viewModel = viewModel)
        }
    }



}