package com.jetbrains.dependoapp.data.dto

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShipmentResponse(
    @SerialName("data")
    val not_attempt: List<NotAttempt>?=null,
    @SerialName("codnot_coltd")
    var codNotCollected: List<CodNotCollectedItem>?=null,
    val status_code: String?=null,
    val error: String?=null,
    val message: String?=null,
) {
    @Stable
    @Serializable
    data class NotAttempt(
        val airwaybilno: String?=null,
        val consignee_first_name: String?=null,
        val consignee_address1: String?=null,
        val consignee_address2: String?=null,
        val destination_city: String?=null,
        val pincode: String?=null,
        val state: String?=null,
        val product: String?=null,
        val item_description: String?=null,
        val pieces: String?=null,
        val telephone1: String?=null,
        val collectable_value: String?=null,
        val to_pay_amount: String?=null,
        val online_paid_amount: String?=null,
        val address: String?=null,
        val awb_type: String?=null,
        val delivery_status: String?=null,
        var payment_status: String?=null,
        val delivery_date: String?=null,
        val delivered_to: String?=null,
        val upi_qr_code: String?=null,
        val transaction_id: String?=null,
        val pay_mode: String?=null,
        val assign_datetime: String?=null,
        val otp_created: String?=null,
        val reason_type: String?=null,
        val sign_img: String?=null,
        val payment_link: String?=null,
        @SerialName("branch_deposit")
        val branchDeposit: String?=null,
        val mainvendor_id: String??=null,
    )
@Serializable
    data class CodNotCollectedItem(
        @SerialName("codnot_coltd_amt")
        val codnotColtdAmt: String ?=null,
        @SerialName("codnot_coltd_cnt")
        val codnotColtdCnt: String ?=null,
        @SerialName("DT")
        val dT: String ?=null,
    )
@Serializable
    data class CustCare(
        val mobile_no: String?=null,
    )
}

@Serializable
data class ShipmentDetailResponse(
    @SerialName("status_code") val statusCode: String,
    @SerialName("error") val error: String,
    @SerialName("message") val message: String,
    @SerialName("data") val data: ResponseData,
)
@Serializable
data class ResponseData(
    @SerialName("airwaybilno") val airwaybilno: String,
    @SerialName("consignee_first_name") val consigneeFirstName: String,
    @SerialName("consignee_address1") val consigneeAddress1: String,
    @SerialName("consignee_address2") val consigneeAddress2: String,
    @SerialName("destination_city") val destinationCity: String,
    @SerialName("pincode") val pincode: String,
    @SerialName("state") val state: String,
    @SerialName("product") val product: String,
    @SerialName("item_description") val itemDescription: String,
    @SerialName("pieces") val pieces: String,
    @SerialName("telephone1") val telephone1: String,
    @SerialName("collectable_value") val collectableValue: String?,
    @SerialName("to_pay_amount") val toPayAmount: String,
    @SerialName("online_paid_amount") val onlinePaidAmount: String?, // Nullable
    @SerialName("address") val address: String,
    @SerialName("awb_type") val awbType: String,
    @SerialName("delivery_status") val deliveryStatus: String,
    @SerialName("payment_status") val paymentStatus: String,
    @SerialName("delivery_date") val deliveryDate: String,
    @SerialName("delivered_to") val deliveredTo: String,
    @SerialName("upi_qr_code") val upiQrCode: String,
    @SerialName("transaction_id") val transactionId: String?, // Nullable
    @SerialName("pay_mode") val payMode: String?, // Nullable
    @SerialName("otp_number") val otpNumber: String,
    @SerialName("otp_created") val otpCreated: String,
    @SerialName("reason_type") val reasonType: String,
    @SerialName("sign_img") val signImg: String,
    @SerialName("payment_link") val paymentLink: String,
    @SerialName("branch_deposit") val branchDeposit: String,
)
