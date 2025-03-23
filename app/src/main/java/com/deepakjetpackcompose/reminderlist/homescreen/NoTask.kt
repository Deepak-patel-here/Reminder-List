package com.deepakjetpackcompose.reminderlist.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepakjetpackcompose.reminderlist.R

@Preview
@Composable
fun NoTask(modifier: Modifier = Modifier) {

    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.notask),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFFF8F00)),
            modifier = Modifier.size(60.dp).alpha(0.7f)
        )
        Text("No task here yet", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.alpha(0.7f))
    }

}