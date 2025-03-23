package com.deepakjetpackcompose.reminderlist.homescreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.reminderlist.datamanager.Task
import com.deepakjetpackcompose.reminderlist.viewmodel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Composable
fun TaskTemplate(task: Task,viewModel: TaskViewModel,modifier: Modifier = Modifier) {
    var isChecked by remember { mutableStateOf(false) }
    var editFlag by remember { mutableStateOf(false) }
    var updatedTask by remember { mutableStateOf(task) }
    val subStr= if(updatedTask.title.length>20){
        updatedTask.title.substring(0,20)+"..."
    }else updatedTask.title

    var flag by remember { mutableStateOf(false) }

    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ){
        Checkbox(
            checked = isChecked,
            onCheckedChange = {isChecked=it},
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.White,
                checkedColor =  Color(0xFFFF8F00)
            )
        )

        Text(
            text=if(flag) task.title else subStr,
            textDecoration = if(isChecked)TextDecoration.LineThrough else TextDecoration.None,
            fontSize = if(isChecked) 14.sp else 18.sp,
            color = if(isChecked) Color.Gray else Color.Black,
            modifier = Modifier.weight(1f).clickable { flag = !flag }
        )

        IconButton(onClick = {editFlag =true}, modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint =  Color(0xFFFF8F00),
                modifier=Modifier
            )
        }

        if(editFlag){
            EditTask(task = task,
                onDismiss ={editFlag=false},
                onUpdate = {
                    newTask->
                    updatedTask=newTask
                    viewModel.updateTask(newTask)
                }
            )
        }
    }
    
}



@Composable
fun CardTemplate(task: Task,viewModel: TaskViewModel,onDelete:()->Unit,modifier: Modifier = Modifier) {
    val deleteArea =300f
    var offsetX by remember { mutableStateOf(0f) }
    val CoroutineScope = rememberCoroutineScope()
    val animateOffsetX by animateDpAsState(targetValue = offsetX.dp, label = "")


    Card (
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .offset(animateOffsetX)
            .pointerInput(Unit){
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if(offsetX>deleteArea){
                            CoroutineScope.launch{
                                onDelete()
                            }
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX+=dragAmount
                }
            }
    ){
        TaskTemplate(task = task, viewModel = viewModel)
    }
    
}

