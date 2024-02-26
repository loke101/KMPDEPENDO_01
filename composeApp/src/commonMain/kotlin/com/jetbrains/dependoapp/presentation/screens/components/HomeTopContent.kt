package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icerockdev.library.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun HomeTopContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.heightIn(min = 150.dp, max = 150.dp)
    ) {
        Image(
            painter = painterResource(MR.images.noshipmentbox_1),
            contentDescription = "",
            modifier = Modifier.size(70.dp).fillMaxWidth(),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = "No Shipment Assigned",
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            textAlign = TextAlign.Center
        )
    }

}