package com.jetbrains.dependoapp.presentation.screens.CallLogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource


class CallLogsScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                CallLogScreen()
            })
    }

}

@Composable
private fun CallLogScreen() {
Box(modifier = Modifier.fillMaxSize()) {
    CallLogsScreenContent()

}
}

@Composable
private fun CallLogsScreenContent() {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(12.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        contentColor = Color.DarkGray,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(colorResource(MR.colors.blackOff)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(MR.images.person_shipment_1),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                        )
                    }

                    Text(text = "Adarash",fontFamily = FontFamily.SansSerif, style = MaterialTheme.typography.body2)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(text = "02:30 pm", style = MaterialTheme.typography.caption, fontFamily = FontFamily.SansSerif)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Text(text = "CC03030345",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 40.dp),fontFamily = FontFamily.SansSerif)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(text = "00:50",style = MaterialTheme.typography.caption, fontFamily = FontFamily.SansSerif)
                }
            }


        }
    }

}

