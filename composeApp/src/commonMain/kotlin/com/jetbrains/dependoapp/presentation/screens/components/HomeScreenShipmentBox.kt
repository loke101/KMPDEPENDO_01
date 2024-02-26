package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ShipmentBoxScreen(
    modifier: Modifier = Modifier,
    imgIcon: Painter,
    shipmentStatus: String? = null,
    shipmentCount: String? = null,
    color: List<Color> = listOf(
        Color.White, Color.Blue
    ),
    onShipmentBoxClicked:()->Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier.clickable {
            onShipmentBoxClicked.invoke()
        }
    ) {
        Box(
            modifier = Modifier
                .height(83.dp)
                .width(145.dp)
                .background(brush = Brush.verticalGradient(colors = color))
                .padding(2.dp)
                .animateContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = imgIcon,
                    contentDescription = "",
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                )
                Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${shipmentStatus.toString()}  (${shipmentCount.toString()})",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center

                        )
                    }
                }


            }
        }
    }
}