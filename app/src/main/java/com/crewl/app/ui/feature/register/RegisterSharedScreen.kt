package com.crewl.app.ui.feature.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crewl.app.ui.component.RegisterProgressBar
import com.crewl.app.ui.feature.login.number.LoginScreen
import com.crewl.app.ui.feature.register.name.RegisterNameScreen
import com.crewl.app.ui.theme.SoftPeach

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterSharedScreen(navigator: NavHostController, viewModel: RegisterSharedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPeach)
            .padding(20.dp)
    ) {
        keyboard?.let {
            Column() {
                RegisterProgressBar(1)
                
                Spacer(modifier = Modifier.height(20.dp))

                RegisterNameScreen(navigator = rememberNavController())
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterSharedScreen() {
    RegisterSharedScreen(navigator = rememberNavController())
}