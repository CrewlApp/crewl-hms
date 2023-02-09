package com.crewl.app.ui.feature.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.crewl.app.R
import com.crewl.app.ui.component.ButtonState
import com.crewl.app.ui.component.AnimatedButton
import com.crewl.app.ui.component.CrewlTextField
import com.crewl.app.ui.component.PrivacyPolicyBottomSheet
import com.crewl.app.ui.router.Screen
import com.crewl.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val KeyboardDelay = 750L

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(navigator: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val keyboard = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    val skipHalfExpanded by remember { mutableStateOf(true) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )
    
    val isPrivacyPolicyClicked = remember {
        mutableStateOf(false)
    }

    val phoneNumber by viewModel.phoneNumber

    LaunchedEffect(key1 = context) {
        viewModel.logUserInEvent.collect() { event ->
            when (event) {
                is LoginEvent.PhoneNumberChanged -> {
                    viewModel.updatePhoneNumber(event.phoneNumber)
                }
                is LoginEvent.SavePhoneNumber -> {
                    viewModel.savePhoneNumber(event.phoneNumber)
                }
                is LoginEvent.SavedPhoneNumberSuccess -> {
                    if (event.status)
                        Toast.makeText(context, "savePhoneSuccess", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                }
                is LoginEvent.Navigate -> {
                    navigator.navigate(route = Screen.AuthenticationLoginScreen.route)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        coroutineScope.launch {
            delay(KeyboardDelay)
            focusRequester.requestFocus()
        }

        onDispose {}
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Yellow,
        sheetBackgroundColor = Color.Red,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ),
        sheetContent = {
            if (!isPrivacyPolicyClicked.value)
                PrivacyPolicyBottomSheet(painter = painterResource(id = R.drawable.img_terms_of_service), text = stringResource(id = R.string.terms_of_service))
            else
                PrivacyPolicyBottomSheet(painter = painterResource(id = R.drawable.img_privacy_policy), text = stringResource(id = R.string.privacy_policy))
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    coroutineScope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        }
                    }
                }
                .padding(20.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.login_header_title),
                        style = LightTypography.h1.copy(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Start
                        ),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.login_header_description),
                        style = MaterialTheme.typography.body1.copy(
                            color = SubtitleColor,
                            textAlign = TextAlign.Start,
                            lineHeight = 24.sp,
                            letterSpacing = (-0.05).sp
                        ),
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row {
                        Box(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .background(White, Shapes.large)
                                .clip(Shapes.large)
                                .border(1.dp, Gray25)
                                .wrapContentSize()
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                                text = "+90",
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = Black,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        CrewlTextField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .onFocusChanged {
                                    if (it.isFocused) keyboard?.show()
                                },
                            value = phoneNumber,
                            onValueChange = {
                                viewModel.onPhoneNumberChanged(phoneNumber = it)
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.phone_number),
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                                        color = FocusedLabelColor
                                    )
                                )
                            },
                            placeholder = {
                                Text(text = stringResource(id = R.string.login_phone_number_placeholder))
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            supportingText = {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                                            append("* ")
                                        }
                                        withStyle(style = SpanStyle(color = FocusedLabelColor)) {
                                            append(stringResource(id = R.string.login_phone_number_supporting_text))
                                        }
                                    },
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
                                        color = FocusedLabelColor
                                    )
                                )
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    keyboard?.let {
                        PrivacyPolicyBox(sheetState, it, isPrivacyPolicyClicked)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    AnimatedButton(text = stringResource(id = R.string.continue_string))
                }
            }
        }

        BackHandler(enabled = sheetState.isVisible) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PrivacyPolicyBox(
    sheetState: ModalBottomSheetState,
    keyboard: SoftwareKeyboardController,
    isPrivacyPolicyClicked: MutableState<Boolean>,
    onButtonClick: () -> Unit = {}
) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    var targetOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val coroutineScope = rememberCoroutineScope()

    val updatedOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 55.dp)
                .zIndex(1.0f)
                .padding(end = 5.dp)
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 2.dp, color = Black, shape = Shapes.small)
                .background(White, shape = Shapes.small)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 10.dp)
                        .size(30.dp)
                        .padding(5.dp)
                        .pointerInput(buttonState) {
                            detectTapGestures(
                                onPress = {
                                    buttonState = if (buttonState == ButtonState.Idle) {
                                        coroutineScope.launch {
                                            updatedOffset.animateTo(targetOffset)
                                        }
                                        ButtonState.Pressed
                                    } else {
                                        coroutineScope.launch {
                                            updatedOffset.animateTo(Offset(0f, 0f))
                                        }
                                        ButtonState.Idle
                                    }

                                    onButtonClick()
                                }
                            )
                        }
                        .clip(Shapes.small)
                        .background(White, Shapes.small)
                        .border(2.dp, Black, Shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    if (buttonState == ButtonState.Pressed)
                        Image(modifier = Modifier.padding(2.dp), painter = painterResource(id = R.drawable.ic_check_24), contentDescription = null)
                }

                val annotatedText = buildAnnotatedString {
                    pushStringAnnotation(
                        tag = "termsOfService",// provide tag which will then be provided when you click the text
                        annotation = "termsOfService"
                    )
                    withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.Bold)) {
                        append("Kullanım Koşulları ")
                    }
                    pop()
                    withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal)) {
                        append("ve")
                    }
                    pushStringAnnotation(
                        tag = "privacyPolicy",// provide tag which will then be provided when you click the text
                        annotation = "privacyPolicy"
                    )
                    withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.Bold)) {
                        append(" Güvenlik Sözleşmesi")
                    }
                    pop()
                    withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal)) {
                        append("‘ni okudum ve kabul ediyorum.")
                    }
                }

                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "termsOfService",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            coroutineScope.launch {
                                keyboard.hide()
                                delay(700L)
                                isPrivacyPolicyClicked.value = false
                                if (!sheetState.isVisible) {
                                    sheetState.show()
                                }
                            }
                        }

                        annotatedText.getStringAnnotations(
                            tag = "privacyPolicy",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            coroutineScope.launch {
                                keyboard.hide()
                                isPrivacyPolicyClicked.value = true
                                delay(1000L)
                                if (!sheetState.isVisible) {
                                    sheetState.show()
                                }
                            }
                        }
                    },
                    style = TextStyle(
                        lineHeight = 20.sp,
                        fontSize = 13.sp
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 55.dp)
                .zIndex(0.0f)
                .padding(start = 5.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    targetOffset = rect.topLeft
                }
                .background(Black, shape = Shapes.small)
                .align(Alignment.BottomEnd)
        ) {}
    }
}