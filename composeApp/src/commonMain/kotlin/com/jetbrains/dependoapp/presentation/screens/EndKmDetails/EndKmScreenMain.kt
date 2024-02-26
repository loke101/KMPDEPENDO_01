package com.jetbrains.dependoapp.presentation.screens.EndKmDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import dev.icerock.moko.resources.compose.painterResource

class EndKmScreenMain:Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                EndKmScreen()
            })

    }
}

@Composable
private fun EndKmScreen() {
EndKmScreenContent()
}

@Composable
private fun EndKmScreenContent() {
    Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), contentAlignment = Alignment.TopCenter) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "End Kilometer Details",
                    style = MaterialTheme.typography.h6,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "10 is your starting kilometer",
                    style = MaterialTheme.typography.caption,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.W600
                )

            }
        }
        Spacer(Modifier.height(30.dp))
        TextField(
            value = "",
            onValueChange = {
            },
            Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            shape = TextFieldDefaults.OutlinedTextFieldShape,
            label = {
                Text(
                    text = "Enter End Kilometer",
                    style = MaterialTheme.typography.caption,
                    fontFamily = FontFamily.SansSerif
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            singleLine = true,
        )
        Spacer(Modifier.height(15.dp))
        TextField(
            value = "0",
            onValueChange = {
            },
            Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            enabled = false,
            shape = TextFieldDefaults.OutlinedTextFieldShape,
            label = {
                Text(
                    text = "Your delivered count",
                    style = MaterialTheme.typography.caption,
                    fontFamily = FontFamily.SansSerif
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            singleLine = true,
        )
        Spacer(Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(0.5f,fill = true).fillMaxWidth()
        ) {
            Image(
                painter = painterResource(MR.images.cam_1),
                contentDescription = "endkm",
                modifier = Modifier
                    .size(40.dp)
                    .fillMaxWidth()
                    .clickable {},
                contentScale = ContentScale.Fit,
            )
            Text(text = "Click to capture")
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = {}, modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor =MaterialTheme.colors.primary),
        ) {
            Text(
                text = "Upload",
                fontFamily = FontFamily.SansSerif,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.padding(start = 3.dp))
            Image(
                Icons.Filled.KeyboardArrowUp,
                modifier = Modifier.size(20.dp),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Upload"
            )

        }
    }
}