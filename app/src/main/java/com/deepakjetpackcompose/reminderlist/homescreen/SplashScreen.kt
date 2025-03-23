package com.deepakjetpackcompose.reminderlist.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import com.deepakjetpackcompose.reminderlist.R


@Composable
fun SplashScreen(navController: NavController,modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("home"){
            popUpTo ("splash"){inclusive=true  }
        }
    }



    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        visible=true
    }
    Column (modifier=Modifier.fillMaxSize().background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Yellow,
                Color.Red
            )
        )
    ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        LottieAnimation(
            composition = composition,
            progress = {progress},
            modifier = modifier.size(200.dp)
        )
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn()+ scaleIn()
        ) {
            Text("Reminder List",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
        }

    }

}