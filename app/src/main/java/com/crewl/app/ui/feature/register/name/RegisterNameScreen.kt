package com.crewl.app.ui.feature.register.name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crewl.app.R
import com.crewl.app.framework.base.BaseEvent
import com.crewl.app.ui.component.BackButton
import com.crewl.app.ui.component.CrewlTextField
import com.crewl.app.ui.component.LongButton
import com.crewl.app.ui.component.ToolbarView
import com.crewl.app.ui.feature.register.RegisterSharedEvent
import com.crewl.app.ui.feature.register.RegisterSharedViewModel
import com.crewl.app.ui.theme.FocusedLabelColor
import com.crewl.app.ui.theme.SoftPeach
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterNameScreen(navigator: NavHostController, viewModel: RegisterSharedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current

    val state by viewModel.registerUserInStateFlow

    LaunchedEffect(key1 = context) {
        viewModel.registerUserInEventChannel.collect { event ->
            when (event) {
                is RegisterSharedEvent.NameChanged -> {}

                is RegisterSharedEvent.SavedNameSuccess -> {}

                is RegisterSharedEvent.SurnameChanged -> {}

                is RegisterSharedEvent.SavedSurnameSuccess -> {}

                is BaseEvent.Navigate -> {
                    viewModel.setLoading(false)

                    val gson: Gson = GsonBuilder().create()
                    val phoneNumberJson = gson.toJson(state.number)

                    navigator.navigate("${event.route}/$phoneNumberJson")
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
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = SoftPeach
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
                        onNameChanged = {
                            viewModel.updateName(it)
                        },
                        onSurnameChanged = {
                            viewModel.updateSurname(it)
                        },
                        name = state.name,
                        surname = state.surname,
                        isKeyboardHidden = {
                            if (!it) keyboard.show() else keyboard.hide()
                        }
                    )
                }
            }

            FooterContent(
                onNavigate = { },
                isLoading = state.isLoading,
                onBackPressed = { }
            )
        }
    }
}

@Composable
private fun HeaderContent() {
    ToolbarView(
        titleId = R.string.WHAT_IS_YOUR_NAME,
        descriptionId = R.string.OTHER_PEOPLE_CAN_SEE_ONLY_YOUR_NAME
    )
}


@Composable
fun MainContent(
    onNameChanged: (TextFieldValue) -> Unit,
    onSurnameChanged: (TextFieldValue) -> Unit,
    name: TextFieldValue,
    surname: TextFieldValue,
    isKeyboardHidden: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            focusRequester.requestFocus()
        }
    }

    CrewlTextField(
        modifier = Modifier
            .focusRequester(focusRequester = focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused)
                    isKeyboardHidden(false)
            },
        value = name,
        onValueChange = {
            onNameChanged(it)
        },
        label = {
            Text(
                text = stringResource(id = R.string.PHONE_NUMBER),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = FocusedLabelColor
                )
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
    )

    Spacer(modifier = Modifier.height(10.dp))

    CrewlTextField(
        modifier = Modifier
            .onFocusChanged { state ->
                if (state.isFocused)
                    isKeyboardHidden(false)
            },
        value = surname,
        onValueChange = {
            onSurnameChanged(it)
        },
        label = {
            Text(
                text = stringResource(id = R.string.PHONE_NUMBER),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = FocusedLabelColor
                )
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
    )
}

@Composable
private fun FooterContent(
    isLoading: Boolean,
    onBackPressed: () -> Unit,
    onNavigate: () -> Unit
) {
    val isUserChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BackButton(onBackPressed = {
                    onBackPressed.invoke()
                })

                Spacer(modifier = Modifier.width(10.dp))

                LongButton(text = stringResource(id = R.string.CONTINUE), isActive = (isUserChecked && !isLoading), isLoading = isLoading) {
                    onNavigate.invoke()
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewRegisterNameScreen() {
    RegisterNameScreen(navigator = rememberNavController())
}