package com.jetbrains.dependoapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.dependoapp.presentation.screens.Home.HomeMainScreen
import com.jetbrains.dependoapp.presentation.screens.Home.HomeScreenContent
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


@Preview
@Composable
fun ShowMe() {
Card(modifier = Modifier.size(width = 100.dp, height = 65.dp)) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Rs 0", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text(text = "Cash to be collected",Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

    }
}

}

