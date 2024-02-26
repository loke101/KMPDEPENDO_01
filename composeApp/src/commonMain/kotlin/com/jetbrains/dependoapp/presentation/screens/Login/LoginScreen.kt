package com.jetbrains.dependoapp.presentation.screens.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.Login.LoginState.LoginNavigate
import com.jetbrains.dependoapp.presentation.screens.Login.LoginState.LoginScreenEvents
import com.jetbrains.dependoapp.presentation.screens.mPinScreen.MPinScreen

import com.jetbrains.dependoapp.presentation.screens.otp.MobileOTPDialog
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoginScreen : Screen,KoinComponent {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val loginViewModel:LoginViewModel  by inject<LoginViewModel>()
        LoginScreenMain(localNavigator, loginViewModel = loginViewModel)
    }

}


@Composable
fun LoginScreenMain(localNavigator: Navigator, loginViewModel: LoginViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val loginState  =  loginViewModel.state.collectAsState().value
    val otpState = loginViewModel.otpDialogState.collectAsState(Dispatchers.Main.immediate).value
    val timerText = loginViewModel.timer.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        ) {
        LoginScreenContent(
            onSendOtp = {
                loginViewModel.loginEvents(LoginScreenEvents.GenOTP)
                        },
            mobileNumber = loginState.mobileNumber,
            onMobileNumberChanged = {
                loginViewModel.onMobileNumberChanged(it)},
            isLoading = loginState.isLoading,)
        if (openDialog.value) {
            MobileOTPDialog(
                openDialog.value,
                onDialogDismiss = {
                openDialog.value  = false
                loginViewModel.otpSentReset()
            }, onOtpConfirm = {
                              loginViewModel.loginEvents(LoginScreenEvents.verifyOtp)
                              },
                otpInput = otpState.otpText,
                onOTPValueChange = {
                    loginViewModel.onOTPTextChanged(it)
                }, counterTimerText = timerText,
                otpSentNumber = { loginState.mobileNumber.toString() }
            )
        }
    }

    LaunchedEffect(loginViewModel.navigateState){
        loginViewModel.navigateState.collectLatest{
            when(it){
                is  LoginNavigate.NavigateToMpinScreen ->{
                    openDialog.value  = false
                    loginViewModel.otpSentReset().also {
                        localNavigator.push(MPinScreen().copy(mobile = "9076664982"))
                    }
                }
                else -> {}
            }
        }
    }
    LaunchedEffect(loginViewModel,loginState.isOtpSent){
        if (loginState.isOtpSent){
            openDialog.value = true
            loginViewModel.otpSentReset()
        }
    }
    LaunchedEffect(loginViewModel,loginState.errorMessage){
          if (loginState.errorMessage!=null){
              scaffoldState.snackbarHostState.showSnackbar(loginState.errorMessage.toString())
              loginViewModel.messageReset()
          }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginScreenContent(
    onSendOtp: (Boolean) -> Unit,
    mobileNumber:String,
    onMobileNumberChanged:(String)->Unit,
    isLoading:Boolean
) {
    val controller = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val maxLength = 10
    Column(
        Modifier
            .background(colorResource(MR.colors.backColor))
            .fillMaxSize(),
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(MR.images.login_back_1),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.98f)
                    .fillMaxHeight(0.90f)
                    .align(Alignment.Center)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 150.dp, top = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(MR.strings.dependo),
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif

                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, start = 10.dp),
            ) {
                Text(
                    text = stringResource(MR.strings.welcome_to),
                    color = Color.Black,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = stringResource(MR.strings.stc_riders_app),
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.Black.copy(alpha = 0.7f),
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = stringResource(MR.strings.please_sign_in_to_continue),
                    Modifier.padding(top = 18.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.DarkGray

                )
                Column(
                    Modifier
                        .padding(top = 75.dp, start = 10.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = {
                            if (it.length <= maxLength){
                                onMobileNumberChanged.invoke(it)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {focusManager.clearFocus()}
                        ),

                        modifier = Modifier.align(Alignment.Start),
                        label = {
                            Text(
                                text = stringResource(MR.strings.enter_your_mobile_number),
                                style = MaterialTheme.typography.caption.copy(fontSize = 12.sp),
                                color = Color.Black.copy(alpha = 0.5f),
                                fontFamily = FontFamily.SansSerif
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = "",
                                tint = Color.DarkGray
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = MaterialTheme.colors.primary,
                        ),
                        singleLine = true,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box {

                            Button(
                                onClick = {
                                    onSendOtp.invoke(true)
                                    controller?.hide()
                                },

                                shape = RoundedCornerShape(10.dp),
                                enabled = !isLoading,
                                colors = ButtonDefaults.buttonColors(),
                            ) {
                                Text(text = "Send OTP", modifier = Modifier.padding(4.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    Icons.Rounded.ArrowForward,
                                    contentDescription = "login",
                                    tint = Color.White
                                )

                            }
                            if (isLoading){
                                CircularProgressIndicator(color = Color.Blue, modifier = Modifier.size(21.dp).align(Alignment.Center))
                            }
                        }
                        
                    }
                }

            }


        }


    }
}