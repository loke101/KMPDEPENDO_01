package com.jetbrains.dependoapp.presentation.screens.splash

import com.jetbrains.dependoapp.AppPref.AppPreferences

import com.jetbrains.dependoapp.utils.PreferenceKeyConst
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.IS_OTP_VERIFICATION_PROCESS_COMPLETED
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


data class SplashState(
    val isLoading:Boolean = false,
    val isMPinSet:Boolean = false,
    val isOtpVerify:Boolean=false,

    )
sealed interface NavigateEventsSplash{
    data object NavigateToMPINSet:NavigateEventsSplash
    data object NavigateToLogin:NavigateEventsSplash
    data object NavigateToHome:NavigateEventsSplash
    data object NavigateToMPINLogin:NavigateEventsSplash

}
class SplashViewModel(private val appPreferences: AppPreferences):ViewModel() {
    private val _splashState = MutableStateFlow(SplashState())
    val splashState : StateFlow<SplashState> = _splashState.asStateFlow()
    private val _navigateState = Channel<NavigateEventsSplash>()
    val navigateState = _navigateState.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    init {
        viewModelScope.launch {
            navigateTo()
        }
    }

    private suspend fun navigateTo(){
       val otpVerification =  appPreferences.getBooleanValue(IS_OTP_VERIFICATION_PROCESS_COMPLETED).first()
        val isMPINSet = appPreferences.getBooleanValue(PreferenceKeyConst.IS_MPIN_SET_KEY).first()
       val isLogged =  appPreferences.getBooleanValue(PreferenceKeyConst.IS_LOGGED_IN_WITH_MPIN_KEY).first()

        if (otpVerification == true && isLogged == true){
            _navigateState.send(NavigateEventsSplash.NavigateToHome)
        }else if (otpVerification==true && isLogged==false){
            _navigateState.send(NavigateEventsSplash.NavigateToMPINLogin)
        }
        else if(otpVerification==false && isLogged==false){
            _navigateState.send(NavigateEventsSplash.NavigateToLogin)
        }
        else if(isLogged==true){
            _navigateState.send(NavigateEventsSplash.NavigateToHome)
        }

        println("isOTPVerificationDone:$otpVerification ismpin:$isMPINSet islogin:$isLogged ")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun isOTPVerificationDone() {
        appPreferences.getBooleanValue(PreferenceKeyConst.IS_OTP_VERIFICATION_PROCESS_COMPLETED)
            .collect { otpVerificationDone ->
                println("isOTPVerificationDone $otpVerificationDone")

            }
    }

    private suspend fun checkIsMPINSet(): Flow<Boolean> = flow {
        appPreferences.getBooleanValue(PreferenceKeyConst.IS_MPIN_SET_KEY)
            .collect{
                println("checkIsMPINSet $it")
                if (it != null) {
                    emit(it)
                }
            }
    }

    private suspend fun checkIsLoggedIn(): Flow<Boolean> = flow {
        appPreferences.getBooleanValue(PreferenceKeyConst.IS_LOGGED_IN_WITH_MPIN_KEY)
            .collectLatest {loggedIN->
                println("checkIsLoggedIn $loggedIN")
                if (loggedIN != null) {
                    emit(loggedIN)
                }
            }
    }


    override fun onCleared() {
        super.onCleared()
        _navigateState.close()
        _navigateState.cancel()
    }

}