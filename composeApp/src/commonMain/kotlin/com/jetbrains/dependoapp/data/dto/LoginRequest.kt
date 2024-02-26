package com.jetbrains.dependoapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val randomid: String,
    val mobile: String,
    val utoken: String,
    @SerialName("x-sign-id") val xSignId: String,
    val device_type: String,
    val app_version: String,
    val fcm_token: String
)



