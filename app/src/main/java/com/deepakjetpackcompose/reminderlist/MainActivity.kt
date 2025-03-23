package com.deepakjetpackcompose.reminderlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepakjetpackcompose.reminderlist.datamanager.Task
import com.deepakjetpackcompose.reminderlist.homescreen.CustomAlert
import com.deepakjetpackcompose.reminderlist.homescreen.ListOfTask
import com.deepakjetpackcompose.reminderlist.homescreen.NoTask
import com.deepakjetpackcompose.reminderlist.homescreen.SearchBar
import com.deepakjetpackcompose.reminderlist.homescreen.SplashScreen
import com.deepakjetpackcompose.reminderlist.ui.theme.ReminderListTheme
import com.deepakjetpackcompose.reminderlist.viewmodel.TaskViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlin.getValue

class MainActivity : ComponentActivity() {
    val viewmodel:TaskViewModel by viewModels()
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            AnimatedNavHost(navController = navController, startDestination = "splash"){
                composable(route = "splash",
                    enterTransition = { fadeIn(animationSpec = tween(1000)) },
                    exitTransition = { fadeOut(animationSpec = tween(1000)) }){
                    SplashScreen(navController)
                }

                composable (route = "home",
                    enterTransition = { expandVertically(animationSpec = tween(1000)) },
                    exitTransition = { scaleOut(targetScale = 1.2f) }
                ){
                    MyApp(viewModel = viewmodel)
                }
            }


        }
    }
}

@Composable
fun MyApp(viewModel: TaskViewModel,modifier: Modifier = Modifier) {
    val tasks by viewModel.allTask.observeAsState(emptyList())
    val searchedTaskList by viewModel.searchResult.observeAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    val showTasks = if(searchedTaskList.isNotEmpty()) searchedTaskList else tasks
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog=true},
                containerColor = Color(0xFFFF8F00),
                contentColor = Color.White,
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ){paddingValues ->  
        Column(modifier = modifier.padding(paddingValues).fillMaxSize()
        ) {

            SearchBar(viewModel = viewModel, modifier = Modifier.fillMaxWidth())
            if(tasks.isEmpty()) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = if (tasks.isEmpty()) Alignment.CenterHorizontally else Alignment.End,
                    verticalArrangement = if (tasks.isEmpty()) Arrangement.Center else Arrangement.spacedBy(
                        2.dp
                    )
                )
                {
                    NoTask(modifier = Modifier.padding(15.dp))

                }
            }

            ListOfTask(showTasks,viewModel)
            if(showDialog){
                CustomAlert(
                    onDismiss = {showDialog=false},
                    addTask = {newTask->
                        viewModel.addTask(Task(title = newTask))
                        showDialog=false
                    }
                )
            }
        }

    }

}

