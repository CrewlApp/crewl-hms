package com.crewl.app.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crewl.app.R
import com.crewl.app.ui.feature.login.LoginSharedViewModel
import com.crewl.app.ui.feature.prehome.PreHomeEvent
import com.crewl.app.ui.router.Screen
import com.crewl.app.ui.theme.Black
import com.crewl.app.utils.rememberWindowInfo

@Composable
fun HomeScreen(navigator: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val windowInfo = rememberWindowInfo()
    val screenWidth = windowInfo.screenWidth
    val screenHeight = windowInfo.screenHeight

    LaunchedEffect(key1 = context) {
        viewModel.preHomeEvent.collect() { event ->
            when (event) {
                is PreHomeEvent.NavigateLogin -> {
                    navigator.navigate(Screen.LoginScreen.route)
                }
                is PreHomeEvent.NavigateRegister -> {
                    navigator.navigate(Screen.OnboardingScreen.route)
                }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .paint(painterResource(id = R.drawable.bg_prehome), contentScale = ContentScale.FillWidth)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(bottom = (screenHeight * 0.25).dp),
            verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .size(25.dp),
                color = Black,
                strokeWidth = 2.dp
            )
        }
    }
}