package com.jetbrains.dependoapp.model

import androidx.compose.runtime.Stable

@Stable
data class Shipments(
    val airwayBillNo: String,
    val consigneeFirstName: String,
    val collectableValue: String,
    val deliveryStatus: String,
    val payMode: String,
)
