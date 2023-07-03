package com.crewl.app.ui.feature.login.authentication

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.crewl.app.R
import com.crewl.app.helper.Constants.KEYBOARD_DELAY
import com.crewl.app.ui.component.LongButton
import com.crewl.app.ui.component.ResendCodeView
import com.crewl.app.ui.component.ToolbarView
import com.crewl.app.ui.feature.login.LoginSharedEvent
import com.crewl.app.ui.feature.login.LoginSharedViewModel
import com.crewl.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthenticationScreen(navigator: NavHostController, viewModel: LoginSharedViewModel) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val state by viewModel.logUserInStateFlow

    val code by viewModel.code

    LaunchedEffect(key1 = context) {
        viewModel.logUserInEvent.collect { event ->
            when (event) {
                is LoginSharedEvent.CodeChanged -> {
                    viewModel.updateCode(code = event.code)
                }
                is LoginSharedEvent.Navigate -> {
                }
                is LoginSharedEvent.Loading -> {
                }
                else -> {}
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        color = SoftPeach
    ) {
        keyboard?.let {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    state.number.withCountryCode.let { phone ->
                        HeaderContent(phone = phone)
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    MainContent(
                        code = code,
                        onCodeChanged = {
                            viewModel.onCodeChanged(code = it)
                        },
                        isKeyboardHidden = {
                            if (!it) keyboard.show() else keyboard.hide()
                        }
                    )
                }
            }

            FooterContent(
                onNavigate = {viewModel.verifyCode()},
                isCodeValid = code.text.length > 5,
                onResendCodeClicked = {}
            )
        }
    }
}

@Composable
private fun HeaderContent(phone: String) {
    ToolbarView(
        titleId = R.string.THANKS_NOW_I_NEED_TO_VERIFY_YOUR_NUMBER,
        descriptionStrings = listOf(phone, stringResource(id = R.string.I_SENT_A_CODE_TO_NUMBER))
    )
}

@Composable
private fun MainContent(
    code: TextFieldValue,
    onCodeChanged: (TextFieldValue) -> Unit,
    isKeyboardHidden: (Boolean) -> Unit,
    isCodeWrong: Boolean = false
) {
    Column {
        AuthenticationInputField(
            code = code,
            onCodeChanged = onCodeChanged,
            isKeyboardHidden = isKeyboardHidden,
            isCodeWrong = isCodeWrong
        )

        Spacer(modifier = Modifier.height(height = 15.dp))

        Text(
            modifier = Modifier.alpha(
                if (!isCodeWrong) 0f else 1f
            ),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = TacoBellRed, fontWeight = FontWeight.Bold)) {
                    append("* ")
                }
                withStyle(style = SpanStyle(color = FocusedLabelColor)) {
                    append(stringResource(id = R.string.CODE_IS_INCORRECT_TRY_AGAIN))
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                color = FocusedLabelColor
            )
        )
    }
}

@Composable
private fun AuthenticationInputField(
    code: TextFieldValue,
    onCodeChanged: (TextFieldValue) -> Unit,
    isKeyboardHidden: (Boolean) -> Unit,
    isCodeWrong: Boolean
) {
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        scope.launch {
            delay(KEYBOARD_DELAY)
            focusRequester.requestFocus()
        }

        onDispose {}
    }

    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester = focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused)
                    isKeyboardHidden(false)
            },
        value = code,
        onValueChange = { value ->
            onCodeChanged(value)
        },
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(times = 6) { index ->
                    AuthenticationBoxItem(
                        value = code.text,
                        index = index,
                        isCodeWrong = isCodeWrong
                    )
                }
            }
        }
    )
}

@Composable
private fun AuthenticationBoxItem(
    value: String,
    index: Int,
    isCodeWrong: Boolean
) {
    val char = when {
        index >= value.length -> ""
        else -> value[index].toString()
    }

    val isFocused = value.length > index

    val updatedOffsetX by animateDpAsState(if (isFocused) (3.dp) else (0.dp))
    val updatedOffsetY by animateDpAsState(if (isFocused) (3.dp) else (0.dp))

    Box(
        modifier = Modifier.size(43.dp), contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .size(40.dp)
                .offset(updatedOffsetX, updatedOffsetY)
                .background(White, Shapes.small)
                .border(2.dp, if (!isCodeWrong) Black else TacoBellRed, Shapes.small)
                .align(Alignment.TopStart)
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = char,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = SpaceGrotesk,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .zIndex(0f)
                .background(Black, Shapes.small)
                .offset(3.dp, 3.dp)
                .align(Alignment.BottomEnd)
        )
    }

    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
private fun FooterContent(
    onResendCodeClicked: () -> Unit,
    isCodeValid: Boolean,
    onNavigate: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ResendCodeView {
                onResendCodeClicked()
            }

            Spacer(modifier = Modifier.height(20.dp))

            LongButton(text = stringResource(id = R.string.CONFIRM_CODE), isActive = isCodeValid) {
                onNavigate()
            }
        }
    }
}