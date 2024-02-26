package com.jetbrains.dependoapp.data.repo

import LoginResponse
import com.jetbrains.dependoapp.data.api.DependoApi
import com.jetbrains.dependoapp.data.dto.DashboardResponse
import com.jetbrains.dependoapp.data.dto.GenOtpResponse

import com.jetbrains.dependoapp.network.NetworkUtils.networkApiCall
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


class DependoRepositoryImpl(private val dependoApi: DependoApi):DependoRepository{
    override suspend fun login(mobile:String,fcmToken:String): Flow<Result<LoginResponse?>> {
        return withContext(Dispatchers.IO){
            networkApiCall { dependoApi.login(mobile,fcmToken) }
        }
    }

    override suspend fun genOtp(mobile: String): Flow<Result<GenOtpResponse?>> {
        return withContext(Dispatchers.IO){
            networkApiCall { dependoApi.genOtp(mobile) }
        }
    }

    override suspend fun verifyOTP(otp: String,mobile: String): Flow<Result<GenOtpResponse?>> {
        return withContext(Dispatchers.IO){
            networkApiCall { dependoApi.verifyOtp(otp,mobile) }
        }
    }

    override suspend fun setMPIN(mobile: String, mPin: String): Flow<Result<GenOtpResponse?>> {
        return withContext(Dispatchers.IO){
            networkApiCall { dependoApi.setMpin(mobile,mPin) }
        }
    }

    override suspend fun loginWithMPIN(
        mobile: String,
        mPin: String,
    ): Flow<Result<GenOtpResponse?>> {
        return withContext(Dispatchers.IO){
            networkApiCall { dependoApi.validateMpin(mobileNumber = mobile, mPin = mPin) }
        }
    }

    override suspend fun getDashboardData(): Flow<Result<DashboardResponse?>> {
        return withContext(Dispatchers.IO){
            networkApiCall { dependoApi.getDashboardData() }
        }.distinctUntilChanged().conflate()
    }

}