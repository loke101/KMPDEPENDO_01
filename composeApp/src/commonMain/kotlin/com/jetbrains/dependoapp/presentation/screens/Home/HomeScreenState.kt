package com.jetbrains.dependoapp.presentation.screens.Home

import LoginResponse
import com.jetbrains.dependoapp.data.dto.DashboardResponse

fun DashboardResponse.toHomeState() = HomeScreenState(
    onlinePaidAmount = this.dashBoardData?.onlinePaidAmount.toString(),
    totalCollectable = this.dashBoardData?.totalCollectable.toString(),
    totalShipments =this.dashBoardData?.totalShipments.toString(),
    remainingToPay = this.dashBoardData?.remainingToPay.toString(),
    delCnt = this.dashBoardData?.status?.delCnt.toString(),
    undelCnt = this.dashBoardData?.status?.undelCnt.toString(),
    unattemptCnt = this.dashBoardData?.status?.unattemptCnt.toString(),
    allCnt = this.dashBoardData?.status?.allCnt.toString(),
    tdCodnotColtdCnt = this.dashBoardData?.codnotColtd?.tdCodnotColtdCnt.toString(),
    tdCodnotColtdAmt = this.dashBoardData?.codnotColtd?.tdCodnotColtdAmt.toString(),
    prCodnotColtdCnt = this.dashBoardData?.codnotColtd?.prCodnotColtdCnt.toString(),
    prCodnotColtdAmt = this.dashBoardData?.codnotColtd?.prCodnotColtdAmt.toString(),
    codOldTotalCount = this.dashBoardData?.codnotColtd?.codOldTotalCount.toString(),
    totalAmount = this.dashBoardData?.codnotColtd?.totalAmount.toString()

)
data class HomeScreenState(
    val onlinePaidAmount: String?="0.00",
    val totalCollectable: String?="0.00",
    val totalShipments: String?="0",
    val remainingToPay: String?="0.00",
    val delCnt: String?="0",
    val undelCnt: String?="0",
    val unattemptCnt: String?="0",
    val allCnt: String?="0",
    val tdCodnotColtdCnt: String?="0.00",
    val tdCodnotColtdAmt: String?="0.00",
    val prCodnotColtdCnt: String?="0.00",
    val prCodnotColtdAmt: String?="0.00",
    val codOldTotalCount: String?="0.00",
    val totalAmount: String?="0.00",
    val isLoading :Boolean?= false,
    val errorMessage :String?=null
)

