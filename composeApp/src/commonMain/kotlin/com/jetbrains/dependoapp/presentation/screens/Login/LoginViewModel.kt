package com.jetbrains.dependoapp.presentation.screens.Login

import androidx.compose.runtime.mutableStateOf
import com.jetbrains.dependoapp.AppPref.AppPreferences
import com.jetbrains.dependoapp.data.repo.DependoRepository
import com.jetbrains.dependoapp.presentation.screens.Login.LoginState.LoginNavigate
import com.jetbrains.dependoapp.presentation.screens.Login.LoginState.LoginScreenEvents
import com.jetbrains.dependoapp.presentation.screens.Login.LoginState.LoginUIState
import com.jetbrains.dependoapp.presentation.screens.Login.LoginState.OTPDialogState
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.IS_OTP_VERIFICATION_PROCESS_COMPLETED

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val dependoRepository: DependoRepository,private val appPreferences: AppPreferences) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUIState())
    val state: StateFlow<LoginUIState> = _loginState.asStateFlow()

    private val _otpDialogState = MutableStateFlow(OTPDialogState())
    val otpDialogState: StateFlow<OTPDialogState> = _otpDialogState.asStateFlow()

    private val _navigateState = Channel<LoginNavigate>()
    val navigateState = _navigateState.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    private val _timer = MutableStateFlow(60L)
    val timer: StateFlow<Long> = _timer
    private var timerJob: Job? = null
    private fun startTimer() {
        if (timerJob?.isActive == true) {
            return
        }
        if (timerJob==null||timerJob?.isCompleted==true){
            timerJob = viewModelScope.launch(Dispatchers.Default) {
                for (i in _timer.value downTo 1) {
                    _timer.value = i
                    delay(1000)
                }
                _timer.value = 60L // Reset the timer value to 60 when it reaches 0
                timerJob?.cancel()
                timerJob = null
            }
        }

    }

    private val fcmToken = mutableStateOf("c_tXby4-RYKAggJbZph9wq:APA91bE-yLIWzSaq70LikIIBT_wVKSZ5pUbplEu-AfIGO0aXAkSnn2rMyM9a6ZEQYnxDiFCrurFOIuGCAcVCgH5paCjRDX3jLz183iuCCN1b_cVlVFww7oQC78Jma77KJ2zRTDS93UlW")

    fun onMobileNumberChanged(inputMobileNumber: String) {
        _loginState.update { currentState ->
            currentState.copy(
                mobileNumber = inputMobileNumber,
            )
        }

    }
    fun onOTPTextChanged(otpText: String) {
        _otpDialogState.update { currentState ->
            currentState.copy(
                otpText = otpText,
            )
        }

    }

    private fun loginValidations():Boolean {
        return _loginState.value.mobileIsNotEmpty()
    }
    private fun otpValidations():Boolean {
        return _otpDialogState.value.otpEmptyValidation()
    }


    fun otpSentReset(){
        _loginState.update {
            it.copy(isOtpSent = false)
        }
    }

    fun messageReset() {
        _loginState.update { currentState ->
            currentState.copy(
                errorMessage = null
            )

        }
        _otpDialogState.update {
            it.copy(errorMessage = null)
        }
    }

    fun loginEvents(loginScreenEvents: LoginScreenEvents){
        when(loginScreenEvents){
            is LoginScreenEvents.Login ->{
                login()
            }
            is LoginScreenEvents.GenOTP->{
                if (loginValidations()){
                    genOtp()
                    _loginState.update {
                        it.copy(isLoading = false, errorMessage = null)
                    }
                }else{
                    _loginState.update {
                        it.copy(isLoading = false, errorMessage = "Please enter mobile number")
                    }
                }
            }
            is LoginScreenEvents.verifyOtp->{
                if (!otpValidations()){
                    _otpDialogState.update {
                        it.copy(errorMessage = "Please enter otp code")
                    }
                }else{
                    verifyOTp()
                }
            }

            else -> {}
        }
    }

    private fun login() {
        viewModelScope.launch {
                dependoRepository.login(mobile =state.value.mobileNumber ,fcmToken.value) .collect {response->
                    response.onSuccess {success->
                        println(success)
                        println("Login-MSG Success:${success?.message}")
                        Napier.d("$success",null,"loginView")
                    }
                    response.onFailure {error->
                        println(error)
                        println("Login-MSG Error:${error}")
                        Napier.d("$error",null,"loginView")

                    }
                }
        }
    }

    private fun verifyOTp(){
        viewModelScope.launch {
            _otpDialogState.update {
                it.copy(isLoading = true)
            }
            try {
                dependoRepository.verifyOTP(otpDialogState.value.otpText,state.value.mobileNumber).collect{otpVerifyResponse->
                    otpVerifyResponse.onSuccess {sucessResponse->
                        sucessResponse?.let {response->
                            if (response.status_code?.equals("2000") == true){
                                _navigateState.send(LoginNavigate.NavigateToMpinScreen)
                                appPreferences.saveBooleanValue(IS_OTP_VERIFICATION_PROCESS_COMPLETED,true)
                                _otpDialogState.update {
                                    it.copy(
                                        isLoading = false,
                                        mobileNumber = state.value.mobileNumber,
                                        errorMessage = response.message.toString()
                                    )
                                }
                            }else{
                                appPreferences.saveBooleanValue(IS_OTP_VERIFICATION_PROCESS_COMPLETED,false)
                                _otpDialogState.update {
                                    it.copy(errorMessage = response.message.toString())
                                }
                            }
                        }

                    }
                    otpVerifyResponse.onFailure {errorResponse->
                        _otpDialogState.update {
                            appPreferences.saveBooleanValue(IS_OTP_VERIFICATION_PROCESS_COMPLETED,false)
                            it.copy(
                                isLoading = false,
                                errorMessage = errorResponse.message.toString()
                            )
                        }
                    }
                }

            }catch (e:Exception){
                e.stackTraceToString()
            }finally {
                _otpDialogState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }

    }
    private fun genOtp(){
        viewModelScope.launch {
            _loginState.update {
                it.copy(isLoading = true, errorMessage = null)
            }
            dependoRepository.genOtp(mobile = state.value.mobileNumber).collect{otpResult->
                otpResult.onSuccess {otpResponse->
                    _loginState.update {
                        if (otpResponse?.status_code.equals("2000")){
                            it.copy(isLoading = false, isOtpSent = true, errorMessage = "OTP Send to ${state.value.mobileNumber} ").also {
                                startTimer()
                            }
                        }else{
                            it.copy(isLoading = false, errorMessage = otpResponse?.message, isOtpSent = false)
                        }
                    }
                }
            }
        }
    }
    fun stopTimer() {
        _timer.value = 60L
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
