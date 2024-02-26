package com.jetbrains.dependoapp.presentation.screens.SendMoneyScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.dependoapp.presentation.screens.components.TopBar


class SendMoneyScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
            SendMoneyScreen()
        })

    }

}

@Composable
private fun SendMoneyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        SendMoneyScreenContent()
    }
}

@Composable
private fun SendMoneyScreenContent() {
        var isChecked by remember { mutableStateOf(false) }
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp),
            contentColor = Color.DarkGray,
            modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(15.dp)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                    },
                    colors = CheckboxDefaults.colors()

                )
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("CC01831221",)
                    Text("Jan 30 20204 12:34 pm",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.caption
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("₹100.00")
            }


        }

    }
