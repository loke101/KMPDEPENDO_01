package com.jetbrains.dependoapp.presentation.screens.Login.LoginState

data class LoginUIState(
    val isLoading:Boolean=false,
    val mobileNumber:String ="",
    val errorMessage: String? = null,
    val helperText:String = "",
    val isSendOtpButtonEnabled: Boolean = false,
    val isOtpSent: Boolean = false,
){
    fun mobileIsNotEmpty(): Boolean {
        return mobileNumber.isNotEmpty()
    }
    fun mobileNumberInputValidation():Boolean{
        return mobileNumber.length == 10
    }
}
data class OTPDialogState(
    val isLoading: Boolean = false,
    val otpText:String = "",
    val errorMessage :String?= null,
    val otpHelperText:String ="",
    val timerText:String = "",
    val mobileNumber: String=""
){
    fun otpEmptyValidation():Boolean{
        return otpText.isNotEmpty()
    }
    fun otpInputValidation():Boolean{
        return otpText.length==6
    }
}

sealed interface LoginScreenEvents{
    data object Login:LoginScreenEvents
    data object GenOTP:LoginScreenEvents
    data object resendOtp:LoginScreenEvents
    data object verifyOtp:LoginScreenEvents
}
sealed interface LoginNavigate{
    data object NavigateToMpinScreen:LoginNavigate
    data object NavigateToHomeScreen:LoginNavigate
}


