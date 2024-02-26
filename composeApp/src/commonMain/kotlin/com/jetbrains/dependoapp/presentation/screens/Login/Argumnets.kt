package com.jetbrains.dependoapp.presentation.screens.Login

import kotlinx.serialization.Serializable

@Serializable
data class SendArguments(
    val mobile: String
) {
    companion object {
        fun empty(): SendArguments {
            return SendArguments("")
        }
    }
}