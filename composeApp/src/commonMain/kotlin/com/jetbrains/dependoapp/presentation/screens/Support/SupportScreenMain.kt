package com.jetbrains.dependoapp.presentation.screens.Support

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import dev.icerock.moko.resources.compose.painterResource


class SupportScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                SupportScreen()
            })
    }

}

@Composable
private fun SupportScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        SupportScreenContent()
    }
}

@Composable
private fun SupportScreenContent() {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Need Help? Contact us:",
            Modifier
                .fillMaxWidth().padding(start = 10.dp)
        )
        Row(Modifier.padding(10.dp, top = 10.dp).clickable {
        }) {
            Image(
                painter = painterResource(MR.images.message_1),
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "dependocare@altruistindia.com", Modifier.fillMaxWidth())
        }
        Row(Modifier.padding(10.dp).clickable {
        }) {
            Image(
                painter = painterResource(MR.images.phone_call_2),
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(start = 10.dp, top = 15.dp))
            Text(text = "+91 7827601177", Modifier.fillMaxWidth())
        }

    }
}