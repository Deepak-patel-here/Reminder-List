package com.deepakjetpackcompose.reminderlist.homescreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.deepakjetpackcompose.reminderlist.viewmodel.TaskViewModel


@Composable
fun SearchBar(viewModel: TaskViewModel,modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }
    TextField(
        value = searchText,
        onValueChange = {searchText=it
                        viewModel.searchTask(searchText)},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        minLines = 1,
        maxLines = 1,
        placeholder = { Text("Search for tasks..") },
        modifier = modifier.padding(15.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),

    )
}