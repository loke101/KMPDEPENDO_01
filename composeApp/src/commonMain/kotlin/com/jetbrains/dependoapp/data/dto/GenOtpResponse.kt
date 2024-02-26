package com.jetbrains.dependoapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenOtpResponse(
    val status_code: String?=null,
    val error: String?=null,
    val message: String?=null
)