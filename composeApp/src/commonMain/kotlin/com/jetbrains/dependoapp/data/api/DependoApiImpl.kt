package com.jetbrains.dependoapp.data.api

import LoginResponse
import androidx.compose.runtime.mutableStateOf
import com.jetbrains.dependoapp.AppPref.AppPreferences
import com.jetbrains.dependoapp.data.api.ApiUtils.API_KEY
import com.jetbrains.dependoapp.data.api.ApiUtils.API_KEY_Dash
import com.jetbrains.dependoapp.data.api.ApiUtils.SECRET_KEY
import com.jetbrains.dependoapp.data.api.ApiUtils.VENDOR_NAME
import com.jetbrains.dependoapp.data.api.ApiUtils.VENDOR_NAME_DASH
import com.jetbrains.dependoapp.data.api.EndPoint.genOtpUrl
import com.jetbrains.dependoapp.data.api.EndPoint.getDashboardDataURL
import com.jetbrains.dependoapp.data.api.EndPoint.loginUrl
import com.jetbrains.dependoapp.data.api.EndPoint.resendOTP
import com.jetbrains.dependoapp.data.api.EndPoint.setMpinURL
import com.jetbrains.dependoapp.data.api.EndPoint.validateMpinURL

import com.jetbrains.dependoapp.data.api.EndPoint.verifyOTPURL
import com.jetbrains.dependoapp.data.dto.DashboardResponse
import com.jetbrains.dependoapp.data.dto.GenOtpResponse
import com.jetbrains.dependoapp.data.dto.LoginRequest
import com.jetbrains.dependoapp.data.dto.ShipmentResponse

import com.jetbrains.dependoapp.decrypt
import com.jetbrains.dependoapp.encrypt
import com.jetbrains.dependoapp.generateSha512
import com.jetbrains.dependoapp.genrateHasPassword
import com.jetbrains.dependoapp.genrateXsignId
import com.jetbrains.dependoapp.getFcmToken
import com.jetbrains.dependoapp.uUIDGenerator
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.FBS_TOKEN_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.USER_ID_KEY
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.User_Mobile
import dev.gitlive.firebase.decode
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpSendInterceptor
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.use
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put
import kotlin.random.Random


class DependoApiImpl(private val apiClient: ApiClient ,private val appPreferences: AppPreferences): DependoApi {

    override suspend fun login(mobile:String,fcmToken:String): LoginResponse? {
        val randomId = uUIDGenerator()
        val genFcmToken = getFcmToken()
        val loginJsonObject = createJsonObject(
            "password" to genrateHasPassword(""),
            "x-sign-id" to genrateXsignId(mobile,randomId),
            "randomid" to randomId,
            "mobile" to mobile,
            "utoken" to "json",
            "device_type" to 1,
            "app_version" to "8.8.4",
            "fcm_token" to genFcmToken
        )
        return apiClient.sendRequest(loginUrl,loginJsonObject)
    }

    override suspend fun genOtp(mobile: String): GenOtpResponse? {
        val token =generateSha512(API_KEY+VENDOR_NAME+mobile+SECRET_KEY)
        val genOtpJsonObject= createJsonObject(
            "token" to token ,
            "mobile" to mobile

        )
        return apiClient.sendRequest(genOtpUrl, jsonObject =genOtpJsonObject ,)
    }

    override suspend fun verifyOtp(otp: String,mobile: String): GenOtpResponse? {
        val token =generateSha512(API_KEY + VENDOR_NAME + mobile + SECRET_KEY)
        val verifyOtpJsonObject= createJsonObject(
            "token" to token ,
            "mobile" to mobile,
            "otp" to otp

        )
        return apiClient.sendRequest(verifyOTPURL, jsonObject = verifyOtpJsonObject,)
    }

    override suspend fun setMpin(mobile: String, mPIN: String): GenOtpResponse? {
        val token =generateSha512(API_KEY + VENDOR_NAME + mobile + SECRET_KEY)
        val setMPinJsonObject= createJsonObject(
            "token" to token ,
            "mobile" to mobile,
            "mpin" to mPIN

        )

        return apiClient.sendRequest(setMpinURL,jsonObject = setMPinJsonObject)
    }

    override suspend fun validateMpin(mobileNumber: String, mPin: String): GenOtpResponse? {
        val token =generateSha512(API_KEY + VENDOR_NAME + mobileNumber + SECRET_KEY)
        val validateMPinJsonObject= createJsonObject(
            "token" to token ,
            "mobile" to mobileNumber,
            "mpin" to mPin

        )
        return apiClient.sendRequest(validateMpinURL,jsonObject = validateMPinJsonObject)
    }

    override suspend fun resendOTP(mobileNumber: String): GenOtpResponse? {
        return apiClient.sendRequest(resendOTP,jsonObject = null)
    }

    override suspend fun getDashboardData(): DashboardResponse? {
        val userId = appPreferences.getStringValue(USER_ID_KEY).first()
        val fbsToken = appPreferences.getStringValue(FBS_TOKEN_KEY).first()
//        val mobile = appPreferences.getStringValue(User_Mobile).first()
        val token =generateSha512(API_KEY_Dash + userId+ VENDOR_NAME_DASH + SECRET_KEY)

        val getDashBoardJsonObject= createJsonObject(
            "user-id" to userId ,
            "fbs_token" to fbsToken,
            "token" to token
        )
        return  apiClient.sendRequest(getDashboardDataURL,jsonObject = getDashBoardJsonObject)
    }


}