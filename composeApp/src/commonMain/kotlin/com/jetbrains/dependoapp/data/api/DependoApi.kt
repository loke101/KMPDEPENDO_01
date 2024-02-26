package com.jetbrains.dependoapp.data.api

import LoginResponse
import com.jetbrains.dependoapp.data.dto.DashboardResponse
import com.jetbrains.dependoapp.data.dto.GenOtpResponse
import com.jetbrains.dependoapp.data.dto.LoginRequest
import com.jetbrains.dependoapp.data.dto.ShipmentResponse

import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow


interface DependoApi {
    suspend fun login(mobile:String,fcmToken:String):LoginResponse?
    suspend fun genOtp(mobile: String):GenOtpResponse?
    suspend fun verifyOtp(otp: String,mobile: String):GenOtpResponse?
    suspend fun setMpin(mobile: String,mPIN:String):GenOtpResponse?
    suspend fun validateMpin( mobileNumber: String, mPin: String,):GenOtpResponse?
    suspend fun resendOTP( mobileNumber: String):GenOtpResponse?
    suspend fun getDashboardData(): DashboardResponse?
//    suspend fun fetchAllShipmentsList(): ShipmentResponse

//    suspend fun sendCustomerSMS(): SmsResponse
//
//    suspend fun checkSMSStatus(): SmsResponse
//
//    suspend fun getSMSTemplates(): SmsTempleteResponse
//
//    suspend fun getShipmentsDetails(): ShipmentDetailResponse
//
//    suspend fun uploadStartKm(): StartKmUploadResponse
//
//    suspend fun getStatusList(): StatusList
//
//    suspend fun updateStatusList(): StatusUpdateResponse
//
//    suspend fun getRelationList(): RelationListResponse
//
//    suspend fun uploadEndKm(): EndKmResponse
//
//    suspend fun fetchNotificationsList(): NotificatonsListResponse
//
//    suspend fun countNotification(): NotificationCountResponse
//
//    suspend fun deleteNotification():ResponseDeleteNotification
//
//    suspend fun readNotification(): ResponseReadNotification
//
//    suspend fun getProofList(): ProofListResponse
//
//    suspend fun checkPaymentStatus(): PaymentStatusResponse
//
//    suspend fun getMissedCallList(): MissedCallResponse
//
//    suspend fun getCodCollectedList(): TransactionHistoryResponse
//
//    suspend fun sendMoneyService(): PaymentResponse
//
//    suspend fun getCodNotCollected_td(): SendMoneyListResponse
//
//    suspend fun getTransactionDetail(): TransactionDetailResponse
//
//    suspend fun getBanners(): BannerResponse
//
//    suspend fun getSearchShipmentDetail(): ShipmentResponse


}