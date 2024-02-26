package com.jetbrains.dependoapp.presentation.screens.Transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import com.jetbrains.dependoapp.presentation.screens.components.TransactionDetailsScreenMain
import dev.icerock.moko.resources.compose.colorResource


class TransactionScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                TransactionScreen(navigateToTransDetailsScreen = {
                    navigator.push(TransactionDetailsScreenMain())
                })
            })
    }

}

@Composable
private fun TransactionScreen(navigateToTransDetailsScreen:()->Unit,) {
    Box(modifier = Modifier.fillMaxSize()) {
        TransactionScreenContent(onTransClick = {
            navigateToTransDetailsScreen.invoke()
        })
    }

}

@Composable
private fun TransactionScreenContent(onTransClick:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(12.dp)
            .clickable {onTransClick()},
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        contentColor = Color.DarkGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transaction Date",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.body2,
                    color = Color.DarkGray
                )
                Text(
                    text = "Jan 28 2024 15:56 pm",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.caption,
                    color = colorResource(MR.colors.blackOff)
                )

            }
            // Transaction ID
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp), // Spacing between rows
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dependo Transaction ID:",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.body2,
                    color = Color.DarkGray
                )
                Text(
                    text = "240129125637020",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.caption,
                    color = colorResource(MR.colors.blackOff)
                )
            }
            // Transaction Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp), // Spacing between rows
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transaction Status:",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.body2,
                    color = Color.DarkGray
                )
                Text(
                    text = "Success",
                    Modifier
                        .background(colorResource(MR.colors.deliveredEndColor))
                        .padding(5.dp),
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.caption,
                    color = Color.White
                )
            }

            // Total Amount
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Amount",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.body2,
                    color = Color.DarkGray
                )
                Text(
                    text = "₹ 200.00",
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.caption,
                    color = Color.DarkGray
                )
            }
        }
    }

}