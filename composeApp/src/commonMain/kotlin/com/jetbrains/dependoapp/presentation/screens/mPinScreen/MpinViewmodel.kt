package com.jetbrains.dependoapp.presentation.screens.mPinScreen

import com.jetbrains.dependoapp.AppPref.AppPreferences
import com.jetbrains.dependoapp.data.repo.DependoRepository
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.EMP_TYPE
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.FBS_TOKEN_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.IS_LOGGED_IN_WITH_MPIN_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.IS_MPIN_SET_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.SIP_CALL_TYPE_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.SIP_ID
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.SIP_PASSWORD_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.USER_ID_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.User_Mobile
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MpinViewmodel(private val dependoRepository: DependoRepository,private val appPreferences: AppPreferences): ViewModel()  {

    private val _mPINScreenState = MutableStateFlow(MpinScreenState())
    val mPINScreenState:StateFlow<MpinScreenState> =  _mPINScreenState.asStateFlow()


    private val _navigationEvents = Channel<MPINNavigateEvents>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun onEvents(mpinEvents: MPINEvents){
        when(mpinEvents){
            is MPINEvents.loginClicked ->{
                loginWithMPIN()
            }
            is MPINEvents.forgotMPINClicked ->{

            }
            is MPINEvents.onSetMpinClicked->{
                setMpin()
            }
            else -> {}
        }

    }

    fun getMobileNumber(mobile: String?) {
        _mPINScreenState.update {
            it.copy(mobileNumber = mobile?:"")
        }

    }
    fun errorMessageReset(){
        _mPINScreenState.update {
            it.copy(errorMessage = null)
        }
    }
    fun onLoginMPINInput(input:String){
        _mPINScreenState.update {
            it.copy(mPinInput = input)
        }

    }
    fun onReEnteredInput(input: String){
        _mPINScreenState.update {
            it.copy(mPinInputReEntered = input)
        }
    }
   private fun setMpin(){
        _mPINScreenState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            dependoRepository.setMPIN(mPINScreenState.value.mobileNumber,mPINScreenState.value.mPinInput).collect{mPinSetResponse->
                mPinSetResponse.let {setMpinResponse->
                    setMpinResponse.onSuccess {sucessResponse->
                        if (sucessResponse?.status_code.equals("2000")){
                            with(appPreferences){
                                saveBooleanValue(IS_MPIN_SET_KEY,true)
                            }
                            login()
                        }
                    }
                    setMpinResponse.onFailure {errror->
                        _mPINScreenState.update {
                            it.copy(isLoading = false,errorMessage = errror.toString())
                        }

                    }

                }

            }

        }
    }
    private fun login() {
        viewModelScope.launch {
            dependoRepository.login(mobile =mPINScreenState.value.mobileNumber ,"") .collect {response->
                response.onSuccess {success->
                    if (success?.statusCode.equals("2000")){
                        _mPINScreenState.update {
                            it.copy(isLoading = false)
                        }.also {
                            with(appPreferences){
                                saveStringValue(FBS_TOKEN_KEY, success?.fbsToken.toString()?:"")
                                saveStringValue(USER_ID_KEY, success?.user?.id.toString()?:"")
                                saveStringValue(User_Mobile, success?.user?.mobileNo.toString()?:"")
                                saveStringValue(EMP_TYPE, success?.empType.toString()?:"")
                                saveStringValue(SIP_PASSWORD_KEY, success?.user?.sipPass.toString()?:"")
                                saveStringValue(SIP_CALL_TYPE_KEY, success?.user?.sipCalling.toString()?:"")
                                saveStringValue(SIP_ID, success?.user?.sipId.toString()?:"")
                                saveBooleanValue(IS_LOGGED_IN_WITH_MPIN_KEY,true)
                            }.also {
                                _navigationEvents.send(MPINNavigateEvents.navigateToHome)
                            }

                        }

                    }else{
                       appPreferences.saveBooleanValue(IS_MPIN_SET_KEY,false)
                    }
                    println(success)
                    println("Login-MSG Success:${success?.message}")
                    Napier.d("$success",null,"loginView")
                }
                response.onFailure {error->
                    _mPINScreenState.update {
                        it.copy(isLoading = false)
                    }
                    println(error)
                    println("Login-MSG Error:${error}")
                    Napier.d("$error",null,"loginView")

                }
            }
        }
    }

    private fun loginWithMPIN(){
        viewModelScope.launch(Dispatchers.IO) {
            _mPINScreenState.update {
                it.copy(isLoading = true)
            }
            dependoRepository.loginWithMPIN(mPINScreenState.value.mobileNumber,mPINScreenState.value.mPinInput).collect{onMPINLoginResponse->
                onMPINLoginResponse.onSuccess {onMPINLoginSucess->
                    if (onMPINLoginSucess?.status_code.equals("2000")){
                        login().also {
                            _mPINScreenState.update {
                                it.copy(errorMessage = null,)
                            }
                        }

                    }else{
                        _mPINScreenState.update {
                            it.copy(isLoading = false,errorMessage = it.errorMessage.toString()).also {
                                appPreferences.saveBooleanValue(IS_LOGGED_IN_WITH_MPIN_KEY,false)
                            }
                        }
                    }

                }
                onMPINLoginResponse.onFailure {onMPINLoginError->
                    onMPINLoginError.printStackTrace()
                    appPreferences.saveBooleanValue(IS_LOGGED_IN_WITH_MPIN_KEY,false)
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _mPINScreenState.update {
            it.copy(errorMessage = null)
        }
    }


}