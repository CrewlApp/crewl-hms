package com.crewl.app.ui.feature.prehome

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crewl.app.R
import com.crewl.app.ui.router.Screen
import com.crewl.app.ui.theme.*
import com.crewl.app.utils.rememberWindowInfo

@Composable
fun PreHomeScreen(navigator: NavHostController, viewModel: PreHomeViewModel = hiltViewModel()) {
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
                    navigator.navigate(Screen.RegisterScreen.route)
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

@Composable
@Preview
fun PreHomePreview() {
    val navigator = rememberNavController()
    PreHomeScreen(navigator = navigator)
}