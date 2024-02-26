package com.jetbrains.dependoapp.presentation.screens.CallingDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource


class CallScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                CallingScreen()
            })
    }

}

@Composable
private fun CallingScreen() {
CallingScreenContent()
}

@Composable
private fun CallingScreenContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f).padding(top = 30.dp)
        ) {
            Text(
                "Adarsh Thakur",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Text(
                "CC03040404",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.caption,
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Text(
                "00:07",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.caption,
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }

        Column(modifier = Modifier.fillMaxWidth().weight(0.9f),verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CallButtons(MR.images.speaker_1, MR.colors.homeAmount)
                Spacer(modifier = Modifier.padding(end = 9.dp))
                CallButtons(MR.images.bluetooth_1, MR.colors.homeAmount)
                Spacer(modifier = Modifier.padding(end = 9.dp))
                CallButtons(MR.images.mute_microphone_1, MR.colors.homeAmount)
                Spacer(modifier = Modifier.padding(end = 9.dp))
                CallButtons(MR.images.pause_1, MR.colors.homeAmount)
            }
            Spacer(modifier = Modifier.padding(vertical = 6.dp))
            Box(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                CallButtons(MR.images.callhang_1, MR.colors.undeliveredEndColor)
            }
        }
    }
}


@Composable
fun CallButtons(painter: ImageResource, colorResource: ColorResource) {
    Column {
        Box(
            modifier = Modifier.size(50.dp)
                .clip(CircleShape)
                .fillMaxWidth()
                .background(colorResource(colorResource)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(painter),"",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

        }

    }


}