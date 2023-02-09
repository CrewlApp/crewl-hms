package com.crewl.app.ui.feature.prehome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crewl.app.R
import com.crewl.app.ui.component.AnimatedButton
import com.crewl.app.ui.router.Screen
import com.crewl.app.ui.theme.*

@Composable
fun PreHomeScreen(navigator: NavHostController, viewModel: PreHomeViewModel = hiltViewModel()) {
    val context = LocalContext.current

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

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(0.4f),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(0.4f),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            AnimatedButton(fraction = 0.80f, foregroundColor = White, text = stringResource(id = R.string.login_string)) {
                viewModel.onLoginClicked()
            }

            Spacer(modifier = Modifier.height(12.dp))

            AnimatedButton(fraction = 0.80f, foregroundColor = BrightGold, text = stringResource(id = R.string.register_string)) {
                viewModel.onRegisterClicked()
            }
        }
    }
}