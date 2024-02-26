package com.jetbrains.dependoapp.data.repo


import LoginResponse
import com.jetbrains.dependoapp.data.dto.DashboardResponse
import com.jetbrains.dependoapp.data.dto.GenOtpResponse
import kotlinx.coroutines.flow.Flow

interface DependoRepository {
    suspend fun login(mobile:String,fcmToken:String): Flow<Result<LoginResponse?>>
    suspend fun genOtp(mobile: String):Flow<Result<GenOtpResponse?>>
    suspend fun verifyOTP(otp:String,mobile: String):Flow<Result<GenOtpResponse?>>
    suspend fun setMPIN(mobile: String,mPin:String):Flow<Result<GenOtpResponse?>>
    suspend fun loginWithMPIN(mobile: String,mPin: String):Flow<Result<GenOtpResponse?>>
    suspend fun getDashboardData():Flow<Result<DashboardResponse?>>
}