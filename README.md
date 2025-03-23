# Reminder List (To-Do List App)

## Introduction
The **Reminder List** is a To-Do List application built using **Jetpack Compose**, **Room Database**, and **MVVM architecture**. It allows users to add, update, delete, and search for tasks efficiently. The app features a user-friendly UI, a splash screen with Lottie animations, and swipe-to-delete functionality.

## Features
- **Database Management with Room** (Entity, DAO, and Database class)
- **MVVM Architecture Implementation** (ViewModel, Repository, LiveData)
- **Adding and Updating Tasks** with AlertDialog
- **Swipe to Delete Functionality**
- **Search Feature for Quick Lookup**
- **Splash Screen with Navigation and Lottie Animation**

## Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **Room Database**
- **MVVM Architecture**
- **LiveData & State Management**
- **Navigation Component**
- **Lottie Animations**

## Project Structure
```
📂 ReminderListApp
 ├── 📂 data
 │   ├── Task.kt (Entity class)
 │   ├── TaskDao.kt (DAO interface)
 │   ├── TaskDatabase.kt (Database instance)
 │   ├── TaskRepository.kt (Repository for database operations)
 ├── 📂 ui
 │   ├── TaskViewModel.kt (ViewModel for managing tasks)
 │   ├── TaskScreen.kt (UI for listing tasks)
 │   ├── EditTaskDialog.kt (Dialog for editing tasks)
 │   ├── TaskItem.kt (Composable function for displaying a task)
 ├── 📂 utils
 │   ├── Navigation.kt (Handles app navigation)
 │   ├── SwipeToDelete.kt (Implements swipe-to-delete gesture)
 ├── 📂 main
 │   ├── MainActivity.kt (Entry point of the app)
 │   ├── SplashScreen.kt (Splash screen with animation)
```

## Database Implementation
### 1. Entity Class (Task.kt)
```kotlin
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)
```
### 2. DAO (Data Access Object) - TaskDao.kt
```kotlin
@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task_table WHERE title LIKE :query")
    fun search(query: String): LiveData<List<Task>>
}
```
### 3. Room Database - TaskDatabase.kt
```kotlin
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

## MVVM Architecture
### 1. Repository - TaskRepository.kt
```kotlin
class TaskRepository(application: Application) {
    private val taskDao: TaskDao
    val allTask: LiveData<List<Task>>

    init {
        val database = TaskDatabase.getDatabase(application)
        taskDao = database.taskDao()
        allTask = taskDao.getAllTasks()
    }

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    fun search(query: String): LiveData<List<Task>> = taskDao.search(query)
}
```
### 2. ViewModel - TaskViewModel.kt
```kotlin
class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(application)
    val allTasks: LiveData<List<Task>> = repository.allTask

    fun addTask(task: Task) = viewModelScope.launch { repository.insertTask(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repository.deleteTask(task) }
    fun updateTask(task: Task) = viewModelScope.launch { repository.updateTask(task) }
    fun searchTask(query: String): LiveData<List<Task>> = repository.search(query)
}
```

## UI Components
### 1. Task Item UI - TaskItem.kt
```kotlin
@Composable
fun TaskItem(task: Task, onDelete: () -> Unit, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .swipeToDelete(onDelete)
    ) {
        Text(text = task.title, modifier = Modifier.weight(1f))
        IconButton(onClick = onEdit) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}
```

### 2. Alert Dialog for Adding and Editing Tasks - EditTaskDialog.kt
```
@Composable
fun EditTaskDialog(task: Task?, onDismiss: () -> Unit, onUpdate: (Task) -> Unit) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            TextField(value = title, onValueChange = { title = it })
        },
        confirmButton = {
            Button(onClick = { onUpdate(Task(id = task?.id ?: 0, title = title)) }) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
```

### 3. Swipe to Delete Functionality - SwipeToDelete.kt
```kotlin
@Composable
fun Modifier.swipeToDelete(onDelete: () -> Unit): Modifier {
    var offsetX by remember { mutableStateOf(0f) }
    return pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                if (offsetX > 300f) onDelete()
            }
        ) { _, dragAmount -> offsetX += dragAmount }
    }
}
```

## Splash Screen Implementation
```kotlin
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("task_screen")
    }
    LottieAnimation(source = R.raw.splash_animation)
}
```

## How to Run the Project
1. Clone this repository.
2. Open it in **Android Studio**.
3. Sync Gradle files.
4. Run the project on an **emulator or a physical device**.

## Conclusion
This **Reminder List** app follows best practices for **Jetpack Compose**, **MVVM**, and **Room Database**, making it scalable and maintainable. It provides a great learning experience in **state management, UI animations, and database handling**.

---
### Developed by **Deepak Patel** 🚀

