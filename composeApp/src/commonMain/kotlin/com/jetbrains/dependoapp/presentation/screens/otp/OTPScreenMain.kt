package com.jetbrains.dependoapp.presentation.screens.otp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.SoftKeyboardInterceptionModifierNode
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun MobileOTPDialog(
    isDialogVisible: Boolean,
    onDialogDismiss: () -> Unit,
    onOtpConfirm:(String)->Unit,
    otpInput:String,
    onOTPValueChange:(String)->Unit,
    counterTimerText: Long,
    otpSentNumber:()->String
) {
    OTPDialog(
        otpInput,
        onOtpChange = {
            onOTPValueChange(it)
                      },
        isDialogVisible,
        onDialogDismiss,
        onOtpConfirm,
        timerText = counterTimerText,
        mobile = otpSentNumber
    )
}


@Composable
private fun OTPDialog(
    otpInput: String,
    onOtpChange: (String) -> Unit,
    isDialogVisible: Boolean,
    onDialogDismiss: () -> Unit,
    onOtpConfirm: (String) -> Unit,
    timerText: Long,
    mobile:()->String
) {
    if (isDialogVisible){
        Dialog(
            onDismissRequest = { onDialogDismiss.invoke()},
            properties = DialogProperties(
                dismissOnClickOutside = false,)
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentHeight(),
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(12.dp),
                elevation = 10.dp,
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primaryVariant)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    // Title
                    Text(
                        text = "Enter OTP",
                        style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
                        color = Color.DarkGray,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text("Resend in ${timerText.toString()}",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h6.copy(fontSize = 12.sp, color = Color.Green),
                        color = Color.DarkGray,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center)
                    Text("OTP Sent to ${mobile.toString()}",
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                        style = MaterialTheme.typography.h6.copy(fontSize = 12.sp, color = Color.Green),
                        color = Color.DarkGray,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(28.dp))
                    OtpTextField(
                        modifier = Modifier.heightIn(min = 50.dp, max = 50.dp),
                        otpText = otpInput,
                        onOtpTextChange = { value, otpInputFilled ->
                            onOtpChange(value)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Text("Submit", color = MaterialTheme.colors.primary,
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                            modifier = Modifier.clickable { onOtpConfirm.invoke(mobile().toString()) }
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                        Text("Cancel",
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                            color = Color.Red,
                            modifier = Modifier.clickable {
                                onDialogDismiss.invoke()
                            }
                            )
                    }



                }
            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(otpText) {
        if (otpText.length ==6) {
            controller?.hide()
            focusManager.clearFocus()
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused ->  Color.Blue
                    else ->  Color.DarkGray
                }, RoundedCornerShape(10.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.h4,
        color = if (isFocused) {
            Color.Blue
        } else {
            Color.DarkGray
        },
        textAlign = TextAlign.Center
    )
}
