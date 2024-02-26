package com.jetbrains.dependoapp.presentation.screens.mPinScreen

data class MpinScreenState(
    val isLoading:Boolean = false,
    val errorMessage:String? =null,
    val mPinInput:String="",
    val mPinInputReEntered:String="",
    val mobileNumber:String=""
){
    fun isMinEmpty():Boolean{
        return mPinInput.isEmpty() || mPinInput.isBlank()
    }
}

sealed interface MPINEvents{
    data object loginClicked:MPINEvents
    data object forgotMPINClicked:MPINEvents
    data object onSetMpinClicked:MPINEvents
}
sealed interface MPINNavigateEvents{
    data object navigateToHome:MPINNavigateEvents
}