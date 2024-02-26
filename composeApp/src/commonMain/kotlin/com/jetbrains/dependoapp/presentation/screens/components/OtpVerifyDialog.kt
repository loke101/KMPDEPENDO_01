package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun VerifyOtpDialogMain(onOk: () -> Unit, isDialogVisible: Boolean, onDismiss: () -> Unit) {

//    DialogWithTextFiled(onDismissRequest = {onDismiss.invoke()},
//        onConfirmation = {onOk.invoke()}, isDialogVisible = isDialogVisible)


    LoginOTPDialogContent(onOk = {
        onOk.invoke()
    },
        isDialogVisible = isDialogVisible,
        onDismiss = { onDismiss.invoke() }
    )

}

@Composable
private fun LoginOTPDialogContent(
    onDismiss: () -> Unit = {},
    onOk: () -> Unit = {},
    startTimer: () -> Unit = {},
    onResend: () -> Unit = {},
    otpCodes: (String) -> Unit = {},
    timerTexts: String? = null,
    reSend: Boolean? = null,
    userMobileNumber: String? = null,
    isDialogVisible: Boolean,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (isDialogVisible) {
            MaterialTheme(shapes = MaterialTheme.shapes) {
                AlertDialog(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(12.dp),
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                        usePlatformDefaultWidth = true
                    ),
                    onDismissRequest = {
                        onDismiss.invoke()
                    },
                    title = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text(
                                text = "Please Enter an OTP",
                                fontFamily = FontFamily.SansSerif,
                                style = MaterialTheme.typography.subtitle1,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                color = Color.DarkGray
                            )
//                        Text(
//                            text = "OTP Sent to 9076664982",
//                            fontFamily = FontFamily.SansSerif,
//                            style = MaterialTheme.typography.caption,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            color = MaterialTheme.colors.onSecondary
//                        )

                        }

                    },

                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                placeholder = {
                                    Text(
                                        "Enter 6 digit OTP Code",
                                        style = MaterialTheme.typography.caption
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                label = {
                                    Text(
                                        text = "Enter OTP Code",
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(),
                                singleLine = true,
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text(
                                    text = "Resend in 05",
                                    fontFamily = FontFamily.SansSerif,
                                    style = MaterialTheme.typography.subtitle2,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .padding(start = 5.dp)

                                )
                                Text(
                                    text = "Resend",
                                    fontFamily = FontFamily.SansSerif,
                                    style = MaterialTheme.typography.caption,
                                    color = Color.Blue,
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .clickable {
                                            onResend.invoke()
                                            startTimer.invoke()

                                        }
                                )
                            }


                        }


                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDismiss.invoke()
                                onOk.invoke()
                            }
                        ) {
                            Text(
                                "Confirm", fontFamily = FontFamily.SansSerif,
                                style = MaterialTheme.typography.button
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                onDismiss.invoke()
                            }
                        ) {
                            Text(
                                "Dismiss", fontFamily = FontFamily.SansSerif,
                                style = MaterialTheme.typography.button
                            )
                        }
                    }
                )
            }

        }

    }

}

@Composable
fun DialogWithTextFiled(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    isDialogVisible: Boolean,
) {
    if (isDialogVisible) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        placeholder = {
                            Text("Enter 6 digit OTP Code", style = MaterialTheme.typography.caption)
                        },
                        shape = RoundedCornerShape(12.dp),
                        label = {
                            Text(
                                text = "Enter OTP Code",
                                style = MaterialTheme.typography.subtitle1,
                                fontFamily = FontFamily.SansSerif
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(),
                        singleLine = true,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            text = "Resend in 05",
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .padding(start = 5.dp)

                        )
                        Text(
                            text = "Resend",
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.caption,
                            color = Color.Blue,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .clickable {
                                }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = { onConfirmation() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }

}