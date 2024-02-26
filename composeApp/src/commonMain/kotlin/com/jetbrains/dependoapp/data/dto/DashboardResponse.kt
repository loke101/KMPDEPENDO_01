package com.jetbrains.dependoapp.data.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardResponse(
    @SerialName("status_code")
    val statusCode: String?=null,
    @SerialName("error")
    val error: String?=null,
    @SerialName("message")
    val message: String?=null,
    @SerialName("data")
    val dashBoardData: Data?=null,
) {
    @Serializable
    data class Data(
        @SerialName("status")
        val status: Status?=null,
        @SerialName("online_paid_amount")
        val onlinePaidAmount: String?=null,
        @SerialName("Total_collectable")
        val totalCollectable: String?=null,
        @SerialName("Total_Shipments")
        val totalShipments: String?=null,
        @SerialName("remaining_to_pay")
        val remainingToPay: String?=null,
        @SerialName("codnot_coltd")
        val codnotColtd: CodnotColtd?=null,
    ) {
        @Serializable
        data class Status(
            @SerialName("del_cnt")
            val delCnt: String?=null,
            @SerialName("undel_cnt")
            val undelCnt: String?=null,
            @SerialName("unattempt_cnt")
            val unattemptCnt: String?=null,
            @SerialName("all_cnt")
            val allCnt: String?=null,
        )
        @Serializable
        data class CodnotColtd(
            @SerialName("td_codnot_coltd_cnt")
            val tdCodnotColtdCnt: String?=null,
            @SerialName("td_codnot_coltd_amt")
            val tdCodnotColtdAmt: String?=null,
            @SerialName("pr_codnot_coltd_cnt")
            val prCodnotColtdCnt: String?=null,
            @SerialName("pr_codnot_coltd_amt")
            val prCodnotColtdAmt: String?=null,
            @SerialName("tot_codnot_coltd_cnt")
            val codOldTotalCount: String?=null,
            @SerialName("tot_codnot_coltd_amt")
            val totalAmount: String?=null,
        )
    }
}
