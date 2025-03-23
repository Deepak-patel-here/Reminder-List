package com.deepakjetpackcompose.reminderlist.homescreen

import android.widget.Button
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deepakjetpackcompose.reminderlist.datamanager.Task

@Composable
fun EditTask(task:Task, onDismiss:()->Unit, onUpdate:(Task)->Unit, modifier: Modifier = Modifier) {
    var updateTask by remember { mutableStateOf(task.title) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Task") },
        text =
            {
                TextField(
                    value = updateTask,
                    onValueChange = { updateTask = it },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    label = {Text("Update Task")}

                )
            }
        ,
        confirmButton = {
            Button(onClick = { onUpdate(task.copy(title = updateTask))
            onDismiss()},
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF8F00),
                    contentColor = Color.White
                )
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() },
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFC3737),
                    contentColor = Color.White
                )
                ) {
                Text("Cancel")
            }
        }
    )

}