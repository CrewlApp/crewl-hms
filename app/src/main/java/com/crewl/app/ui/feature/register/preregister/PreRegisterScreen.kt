package com.crewl.app.ui.feature.register.preregister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crewl.app.R
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.framework.base.BaseEvent
import com.crewl.app.ui.component.BackButton
import com.crewl.app.ui.component.LongButton
import com.crewl.app.ui.component.PrivacyPolicyButton
import com.crewl.app.ui.feature.login.LoginSharedEvent
import com.crewl.app.ui.feature.prehome.PreHomeViewModel
import com.crewl.app.ui.feature.register.RegisterSharedViewModel
import com.crewl.app.ui.router.Screen
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.Inter
import com.crewl.app.ui.theme.SoftPeach
import com.crewl.app.ui.theme.SpaceGrotesk
import com.crewl.app.ui.theme.SubtitleColor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PreRegisterScreen(navigator: NavHostController, viewModel: RegisterSharedViewModel = hiltViewModel(), phoneNumber: PhoneNumber) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = context) {
        viewModel.registerUserInEventChannel.collect { event ->
            when (event) {
                is BaseEvent.Navigate -> {
                    viewModel.setLoading(false)
                    navigator.navigate(event.route)
                }

                is BaseEvent.Loading -> {
                    keyboard?.hide()
                    viewModel.setLoading(true)
                }

                is BaseEvent.Error -> {
                    keyboard?.show()
                    viewModel.setLoading(false)

                    Timber.tag("App.tag").i("AuthenticationScreen: %s", event.message)
                }

                else -> {}
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        keyboard?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    MainContent(phoneNumber.withCountryCode)
                }
            }

            FooterContent(onNavigate = {
                navigator.navigate(Screen.RegisterScreen.route)
            })
        }
    }
}

@Composable
fun MainContent(phoneNumber: String) {
    Image(
        modifier = Modifier
            .width(250.dp),
        painter = painterResource(id = R.drawable.img_uzayli),
        contentDescription = "Pager Image"
    )

    Spacer(modifier = Modifier.height(40.dp))

    Text(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(horizontal = 10.dp),
        text = stringResource(id = R.string.SUCCESSFULLY_VERIFIED_I_GUESS_YOU_ARE_REALLY_A_HUMAN),
        fontFamily = SpaceGrotesk,
        fontSize = 24.sp,
        color = Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        text = stringResource(id = R.string.LETS_CONTINUE),
        fontFamily = Inter,
        color = Black,
        fontSize = 16.sp,
        letterSpacing = (-0.25).sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
}

@Composable
fun FooterContent(
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LongButton(text = "Devam Et", isActive = (true), isLoading = false) {
                    onNavigate.invoke()
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewPreRegisterScreen() {
}