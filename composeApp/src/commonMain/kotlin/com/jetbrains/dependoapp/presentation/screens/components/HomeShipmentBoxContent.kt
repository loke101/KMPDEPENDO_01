package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icerockdev.library.MR
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun HomeBoxContent(
    onShipmentBoxClicked:()->Unit,
    unDeliveredShipmentCount:String?,
    deliveredShipmentCount:String?,
    notAttemptShipmentCount:String?,
    totalShipmentCount:String?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ShipmentBoxScreen(   //Undelivered
                imgIcon = painterResource(MR.images.undelivered_1),
                shipmentStatus = "Undelivered",
                shipmentCount = unDeliveredShipmentCount,
                color = listOf(
                    colorResource(MR.colors.undeliveredStartColor),
                    colorResource(MR.colors.undeliveredEndColor)
                ),
               onShipmentBoxClicked = {onShipmentBoxClicked.invoke()},

            )
            Spacer(modifier = Modifier.padding(horizontal = 20.dp))
            ShipmentBoxScreen(   //NotAttempted
                imgIcon = painterResource(MR.images.notattempted_1),
                shipmentStatus = "Not Attempted",
                shipmentCount = notAttemptShipmentCount?:"0",
                color = listOf(
                    colorResource(MR.colors.notAttemeptedStartColor),
                    colorResource(MR.colors.notAttemeptedEndColor)
                ),
                onShipmentBoxClicked = {onShipmentBoxClicked.invoke()},
            )

        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ShipmentBoxScreen(   //Undelivered
                imgIcon = painterResource(MR.images.deliveredimg_1),
                shipmentStatus = "Delivered",
                shipmentCount = deliveredShipmentCount?:"0",
                color = listOf(
                    colorResource(MR.colors.deliveredStartColor),
                    colorResource(MR.colors.deliveredEndColor)
                ),
                onShipmentBoxClicked = {onShipmentBoxClicked.invoke()},
            )
            Spacer(modifier = Modifier.padding(horizontal = 20.dp))
            ShipmentBoxScreen(   //NotAttempted
                imgIcon = painterResource(MR.images.boxes_2),
                shipmentStatus = "Total Shipments",
                shipmentCount = totalShipmentCount?:"0",
                color = listOf(
                    colorResource(MR.colors.totalStartColor),
                    colorResource(MR.colors.totalEndColor)
                ),
                onShipmentBoxClicked = {onShipmentBoxClicked.invoke()},
            )
        }
    }


}