package com.crewl.app.ui.feature.login

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.crewl.app.data.model.country.Country
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.domain.usecase.authentication.CheckUserUseCase
import com.crewl.app.domain.usecase.login.SearchCountryUseCase
import com.crewl.app.domain.usecase.authentication.SignInUseCase
import com.crewl.app.domain.usecase.authentication.SignUpUseCase
import com.crewl.app.framework.base.BaseViewModel
import com.crewl.app.framework.base.ViewState
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.formatCountry
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.getCountry
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.getCountryFromLocal
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.getEmojiFromLocal
import com.crewl.app.ui.router.Screen
import com.crewl.app.utils.RegexValidation
import com.crewl.app.utils.isValid
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

enum class BottomSheetScreenType {
    Empty, PrivacyPolicy, TermsOfService, CountryCode
}

@HiltViewModel
class LoginSharedViewModel @Inject constructor(
    private val searchCountryUseCase: SearchCountryUseCase,
    private val checkUserUseCase: CheckUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {
    private val _logUserInEventChannel = Channel<LoginSharedEvent>()
    val logUserInEvent: Flow<LoginSharedEvent>
        get() = _logUserInEventChannel.receiveAsFlow()

    private val _logUserInStateFlow = mutableStateOf(LoginSharedState())
    val logUserInStateFlow: State<LoginSharedState>
        get() = _logUserInStateFlow

    private val _code = mutableStateOf(TextFieldValue(""))
    val code: State<TextFieldValue>
        get() = _code

    private val _auth: FirebaseAuth = FirebaseAuth.getInstance()
    val auth: FirebaseAuth
        get() = _auth

    private val _isBottomSheetActive = mutableStateOf(false)
    val isBottomSheetActive: State<Boolean>
        get() = _isBottomSheetActive

    private var isFirstInitialization = true

    private var phoneVerificationListener = PhoneVerificationListener()
    private var storedVerificationId: String? = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var credential: PhoneAuthCredential? = null

    private var timeoutJob: Job? = null

    private var phoneVerificationBuilder: PhoneAuthOptions.Builder? = _auth.let {
        PhoneAuthOptions.newBuilder(it)
    }

    init {
        checkUserCountryFromLocal()
    }

    fun initFirebasePhoneAuth(activity: Activity) {
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        phoneVerificationBuilder?.apply {
            setActivity(activity)
            setTimeout(RESEND_OTP_TIMEOUT, TimeUnit.SECONDS)
            setCallbacks(phoneVerificationListener)
        }
    }

    fun requestCode() {
        val options = phoneVerificationBuilder?.setPhoneNumber(_logUserInStateFlow.value.number.withCountryCode)?.build() ?: return
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendCode() {
        val token = resendToken ?: return
        val options = phoneVerificationBuilder
            ?.setForceResendingToken(token)
            ?.build() ?: return
        PhoneAuthProvider.verifyPhoneNumber(options)

        viewModelScope.launch {
            _logUserInEventChannel.send(LoginSharedEvent.Loading)
        }

        timeoutResendCode()
    }

    fun onFirebaseInitialization() {

    }

    fun setLoading(isLoading: Boolean) {
        _logUserInStateFlow.value = _logUserInStateFlow.value.copy(isLoading = isLoading)
    }

    fun verifyCode() {
        credential = PhoneAuthProvider.getCredential(storedVerificationId ?: TODO("Handle operation."), code.value.text)
        viewModelScope.launch {
            val credential = credential ?: return@launch
            val token = signInWithPhoneAuthCredential(credential)
            onNavigate(route = Screen.PreHomeScreen.route)
        }
    }

    private suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): String {
        val user: FirebaseUser = suspendCoroutine {
            viewModelScope.launch {
                _logUserInEventChannel.send(LoginSharedEvent.Loading)
            }
            _auth.signInWithCredential(credential).addOnCompleteListener(AuthCompleteListener(it))
        }

        val tokenId: String = suspendCoroutine {
            viewModelScope.launch {
                _logUserInEventChannel.send(LoginSharedEvent.Loading)
            }
            user.getIdToken(false).addOnCompleteListener(TokenIdCompleteListener(it))
        }

        return tokenId
    }

    private fun timeoutResendCode() {
        timeoutJob?.cancel()
        timeoutJob = viewModelScope.launch {
            timeoutResendCode(RESEND_OTP_TIMEOUT)
        }
    }

    private suspend fun timeoutResendCode(timeout: Long) {
        delay(ONE_SECOND)
        val remains = timeout - 1
        if (remains >= 0) timeoutResendCode(remains)
    }

    private inner class PhoneVerificationListener :
        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            this@LoginSharedViewModel.credential = credential
            viewModelScope.launch {
                onNavigate(route = Screen.PreHomeScreen.route)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token
            isFirstInitialization = false
            onNavigate(route = Screen.AuthenticationScreen.route)
            timeoutResendCode()
        }
    }

    private inner class AuthCompleteListener(
        private val continuation: Continuation<FirebaseUser>
    ) : OnCompleteListener<AuthResult> {
        override fun onComplete(task: Task<AuthResult>) {
            if (task.isSuccessful) {
                Log.i("App.tag", "onComplete: called.")
                    onNavigate(
                        Screen.PreHomeScreen.route
                    )
            } else if (task.exception is FirebaseAuthInvalidCredentialsException)
                continuation.resumeWith(Result.failure(InvalidVerificationCodeError()))
            else
                continuation.resumeWith(Result.failure(UnexpectedError()))
        }
    }

    private inner class TokenIdCompleteListener(
        private val continuation: Continuation<String>
    ) : OnCompleteListener<GetTokenResult> {
        override fun onComplete(task: Task<GetTokenResult>) {
            if (task.isSuccessful) {
                val token = task.result?.token
                if (token.isNullOrEmpty()) continuation.resumeWith(Result.failure(TokenIdIsNullError()))
                else continuation.resumeWith(Result.success(token))
            } else {
                continuation.resumeWith(Result.failure(UnexpectedError()))
            }
        }
    }

    fun saveNumber(number: PhoneNumber) {
        if (number.isValid) {
            onSavePhoneNumberSuccess(status = true)

            return
        }

        onSavePhoneNumberSuccess(status = false)
    }

    /**
     * @param number as TextFieldValue
     *
     * Change _phoneNumber value with given parameter.
     */
    fun updatePhoneNumber(number: TextFieldValue) = viewModelScope.launch {
        _logUserInStateFlow.value.apply {
            _logUserInStateFlow.value = copy(
                phoneNumber = number
            )
        }
    }

    /**
     * @param code as TextFieldValue
     *
     * Change _code value with given parameter.
     */
    fun updateCode(code: TextFieldValue) = viewModelScope.launch(Dispatchers.IO) {
        if (code.text.length < 7)
            _code.value = code
    }

    /**
     * @param type as BottomSheetType
     *
     * Change _bottomSheetType value with given parameter.
     */
    private fun changeBottomSheetType(type: BottomSheetScreenType) = viewModelScope.launch {
        _logUserInStateFlow.value = _logUserInStateFlow.value.copy(bottomSheetType = type)
    }

    // region UI events
    /**
     * @param code as TextFieldValue
     *
     * @see LoginSharedEvent.CodeChanged
     */
    fun onCodeChanged(code: TextFieldValue) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginSharedEvent.CodeChanged(code = code))
    }

    /**
     * @param phoneNumber as TextFieldValue
     * @see LoginSharedEvent.PhoneNumberChanged
     */
    fun onPhoneNumberChanged(phoneNumber: TextFieldValue) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginSharedEvent.PhoneNumberChanged(number = phoneNumber))
    }

    /**
     * @param status as Boolean
     * @see LoginSharedEvent.SavedPhoneNumberSuccess
     */
    private fun onSavePhoneNumberSuccess(status: Boolean) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginSharedEvent.SavedPhoneNumberSuccess(status = status))
    }

    fun onCountryUpdated(country: Country) = viewModelScope.launch {
        formatCountry(country = country).also { formattedCountry: String ->
            _logUserInStateFlow.value = _logUserInStateFlow.value.copy(countryCode = formattedCountry)
            _logUserInStateFlow.value = _logUserInStateFlow.value.copy(country = country)
        }
    }

    /**
     * @see LoginSharedEvent.SavePhoneNumber
     */
    fun onSavePhoneNumber() = viewModelScope.launch {
        _logUserInStateFlow.value.apply {
            val number = PhoneNumber(country = country, phoneNumber.text)
            _logUserInStateFlow.value = this.copy(number = number)

            _logUserInEventChannel.send(LoginSharedEvent.SavePhoneNumber(number))
        }
    }

    /**
     * @see LoginSharedEvent.Navigate
     */
    fun onNavigate(route: String) = viewModelScope.launch {
        _logUserInEventChannel.send(LoginSharedEvent.Navigate(route = route))
    }

    /**
     * @param type as BottomSheetType
     * @see LoginSharedEvent.ToggleBottomSheet
     */
    fun onBottomSheetClicked(type: BottomSheetScreenType) = viewModelScope.launch {
        delay(500L)

        _isBottomSheetActive.value = true
        changeBottomSheetType(type = type)
        _logUserInEventChannel.send(LoginSharedEvent.ToggleBottomSheet(type = type))
    }
    // endregion

    private fun checkUserCountryFromLocal() = viewModelScope.launch {
        getCountryFromLocal()?.let { country: Country ->
            delay(1500L)

            formatCountry(country = country).also { formattedCountry: String ->
                _logUserInStateFlow.value = _logUserInStateFlow.value.copy(countryCode = formattedCountry)
            }
            _logUserInStateFlow.value = _logUserInStateFlow.value.copy(country = country)
        } ?: let {
            val defaultCountry = getCountry("tr")
            val formattedCountry = "${getEmojiFromLocal(defaultCountry!!.code)} ${defaultCountry.code}"

            _logUserInStateFlow.value = _logUserInStateFlow.value.copy(countryCode = formattedCountry)
            _logUserInStateFlow.value = _logUserInStateFlow.value.copy(country = defaultCountry)
        }
    }

    fun checkIfUserExists(number: String) = viewModelScope.launch {
        call {
            checkUserUseCase.execute(number = number)
        }.collect {
            when (it) {
                is ViewState.Empty -> {}
                is ViewState.Loading -> {}
                is ViewState.RenderFailure -> {}
                is ViewState.RenderSuccess<Boolean> -> {}
            }
        }
    }

    companion object {
        private const val TAG = "FirebaseAuth"
        private const val RESEND_OTP_TIMEOUT = 60L
        private const val ONE_SECOND = 1000L
    }
}