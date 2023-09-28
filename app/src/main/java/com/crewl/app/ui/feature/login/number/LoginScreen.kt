package com.crewl.app.ui.feature.login.number

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crewl.app.R
import com.crewl.app.framework.base.BaseEvent
import com.crewl.app.framework.extension.currentActivity
import com.crewl.app.framework.extension.isInternetAvailable
import com.crewl.app.ui.component.*
import com.crewl.app.ui.feature.login.BottomSheetScreenType
import com.crewl.app.ui.feature.login.LoginSharedEvent
import com.crewl.app.ui.feature.login.LoginSharedViewModel
import com.crewl.app.utils.transformation.PhoneVisualTransformation
import com.crewl.app.ui.theme.*
import com.crewl.app.ui.widgets.CountryCodeScreen
import com.crewl.app.ui.widgets.PrivacyPolicyScreen
import com.crewl.app.ui.widgets.TermsOfServiceScreen
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(navigator: NavHostController, viewModel: LoginSharedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val isInternetConnectionActive = context.isInternetAvailable()
    viewModel.updateInternetConnection(isInternetConnectionActive)

    val scope = rememberCoroutineScope()

    val state by viewModel.logUserInStateFlow
    val isSkipHalfExpanded by remember { mutableStateOf(true) }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = isSkipHalfExpanded
    )

    val isBottomSheetActive by viewModel.isBottomSheetActive

    LaunchedEffect(key1 = context) {
        val activity = context.currentActivity ?: return@LaunchedEffect
        viewModel.initFirebasePhoneAuth(activity)

        viewModel.logUserInEvent.collect { event ->
            when (event) {
                is LoginSharedEvent.PhoneNumberChanged -> {
                    viewModel.setLoading(false)

                    viewModel.updatePhoneNumber(event.number)
                }

                is LoginSharedEvent.SavePhoneNumber -> {
                    viewModel.setLoading(false)

                    viewModel.saveNumber(event.number)
                }

                is LoginSharedEvent.SavedPhoneNumberSuccess -> {
                    viewModel.setLoading(false)

                    if (event.status)
                        viewModel.requestCode()
                    else {
                        // TODO(Show error.)
                    }
                }

                is BaseEvent.Loading -> {
                    viewModel.setLoading(true)
                }

                is BaseEvent.Navigate -> {
                    viewModel.setLoading(false)

                    navigator.navigate(route = event.route)
                }

                is LoginSharedEvent.ToggleBottomSheet -> {
                    viewModel.setLoading(false)

                    if (!bottomSheetState.isVisible) bottomSheetState.show() else bottomSheetState.hide()
                }

                is LoginSharedEvent.CodeChanged -> {

                }

                is BaseEvent.Error -> {
                    Timber.tag("App.tag").i("LoginScreen: %s", event.message)
                }

                is BaseEvent.InternetConnection -> {
                    Timber.tag("App.tag").i("InternetConnection: %s", event.isActive)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ),
        scrimColor = Black.copy(alpha = 0.50f),
        sheetContent = {
            if (isBottomSheetActive) {
                when (state.bottomSheetType) {
                    BottomSheetScreenType.TermsOfService -> {
                        CrewlSheetContent(
                            header = { TermsOfServiceScreen.Header() },
                            main = { TermsOfServiceScreen.Main() },
                            footer = {}
                        )
                    }

                    BottomSheetScreenType.PrivacyPolicy -> {
                        CrewlSheetContent(
                            header = { PrivacyPolicyScreen.Header() },
                            main = { PrivacyPolicyScreen.Main() },
                            footer = {}
                        )
                    }

                    BottomSheetScreenType.CountryCode -> {
                        CrewlSheetContent(
                            header = { CountryCodeScreen.Header() },
                            main = {
                                CountryCodeScreen.Main(
                                    onItemSelected = {
                                        viewModel.onCountryUpdated(country = it)
                                        scope.launch {
                                            bottomSheetState.hide()
                                        }
                                    },
                                    isKeyboardHidden = {
                                        if (!it) keyboard?.show() else keyboard?.hide()
                                    }
                                )
                            },
                            footer = {}
                        )
                    }

                    BottomSheetScreenType.Empty -> {}
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    scope.launch {
                        if (bottomSheetState.isVisible)
                            bottomSheetState.hide()
                    }
                }
                .padding(20.dp)
        ) {
            keyboard?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        HeaderContent()

                        Spacer(modifier = Modifier.height(30.dp))

                        MainContent(
                            onBottomSheetClicked = {
                                viewModel.onBottomSheetClicked(type = BottomSheetScreenType.CountryCode)
                            },
                            onPhoneNumberChanged = {
                                viewModel.onPhoneNumberChanged(phoneNumber = it)
                            },
                            countryCode = state.countryCode,
                            phoneNumber = state.phoneNumber,
                            isKeyboardHidden = {
                                if (!it) keyboard.show() else keyboard.hide()
                            }
                        )
                    }
                }

                FooterContent(
                    isKeyboardHidden = {
                        if (it) keyboard.hide()
                    },
                    isPhoneNumberValid = state.phoneNumber.text.length > 9,
                    onPrivacyPolicyClicked = {
                        viewModel.onBottomSheetClicked(type = BottomSheetScreenType.TermsOfService)
                    },
                    onTermsOfServiceClicked = {
                        viewModel.onBottomSheetClicked(type = BottomSheetScreenType.PrivacyPolicy)
                    },
                    isLoading = state.isLoading,
                    onBackPressed = {
                        navigator.popBackStack()
                    },
                    onNavigate = {
                        viewModel.onSavePhoneNumber()
                    }
                )
            }
        }

        BackHandler(enabled = bottomSheetState.isVisible) {
            scope.launch {
                bottomSheetState.hide()
            }
        }
    }
}

@Composable
private fun HeaderContent() {
    ToolbarView(
        titleId = R.string.WHAT_IS_YOUR_PHONE_NUMBER,
        descriptionId = R.string.FOR_EVERYONE_TO_BE_SAFE_IN_CREWL_I_NEED_TO_CONFIRM
    )
}

@Composable
private fun MainContent(
    onBottomSheetClicked: () -> Unit,
    onPhoneNumberChanged: (TextFieldValue) -> Unit,
    countryCode: String,
    phoneNumber: TextFieldValue,
    isKeyboardHidden: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            focusRequester.requestFocus()
        }
    }

    Row {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .background(White, Shapes.small)
                .clip(Shapes.small)
                .border(1.dp, Gray25)
                .wrapContentSize()
        ) {
            if (countryCode.isEmpty())
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .size(17.dp),
                    color = BrightGold,
                    strokeWidth = 2.dp
                )
            else
                Text(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .clickable {
                            isKeyboardHidden(true)
                            onBottomSheetClicked()
                        },
                    text = countryCode,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
        }

        Spacer(modifier = Modifier.width(10.dp))

        CrewlTextField(
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester)
                .onFocusChanged { state ->
                    if (state.isFocused)
                        isKeyboardHidden(false)
                },
            value = phoneNumber,
            onValueChange = { value ->
                if (value.text.length <= 10)
                    onPhoneNumberChanged(value)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.PHONE_NUMBER),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
                        color = FocusedLabelColor
                    )
                )
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
                            append(stringResource(id = R.string.MAKE_SURE_YOU_ENTERED_PHONE_NUMBER_CORRECTLY))
                        }
                    },
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        color = FocusedLabelColor
                    )
                )
            },
            visualTransformation = PhoneVisualTransformation(
                mask = "(000) 000 00 00",
                maskNumber = '0'
            )
        )
    }
}

@Composable
private fun FooterContent(
    onPrivacyPolicyClicked: () -> Unit,
    onTermsOfServiceClicked: () -> Unit,
    isKeyboardHidden: (Boolean) -> Unit,
    isPhoneNumberValid: Boolean,
    isLoading: Boolean,
    onBackPressed: () -> Unit,
    onNavigate: () -> Unit
) {
    var isUserChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PrivacyPolicyButton(
                isKeyboardHidden = {
                    isKeyboardHidden(true)
                },
                isUserChecked = {
                    isUserChecked = it
                },
                onPrivacyPolicyClicked = {
                    onPrivacyPolicyClicked()
                },
                onTermsOfServiceClicked = {
                    onTermsOfServiceClicked()
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                BackButton(onBackPressed = {
                    onBackPressed.invoke()
                })

                Spacer(modifier = Modifier.width(10.dp))

                LongButton(text = stringResource(id = R.string.SEND_CODE), isActive = (isUserChecked && isPhoneNumberValid && !isLoading), isLoading = isLoading) {
                    onNavigate.invoke()
                }
            }


        }
    }
}

@Composable
@Preview
fun PreviewLoginScreen() {
    val navigator = rememberNavController()
    LoginScreen(navigator = navigator)
}