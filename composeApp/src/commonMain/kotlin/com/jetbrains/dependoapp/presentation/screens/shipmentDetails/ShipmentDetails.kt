package com.jetbrains.dependoapp.presentation.screens.shipmentDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource


class AirwayBillDetailsScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                AwbDetailsScreenMain()
            })

    }

}

@Composable
fun AwbDetailsScreenMain() {
    AwbDetailsScreenContent()
}

@Composable
private fun AwbDetailsScreenContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Card(
                elevation = 10.dp,
                contentColor = Color.DarkGray,
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(10.dp)
            ){
                Column(verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(15.dp)
                ) {
                    CustomRow(label = "Airway Bill No", value = "Total")
                    CustomRowBody("CC001066992","₹500.00")
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painterResource(MR.images.map_1),"", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                        Text("Shipping Address",fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp))

                        Spacer(Modifier.weight(1f))

                        Image(painterResource(MR.images.phone_call_2),"", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                        Image(painterResource(MR.images.sms_1),"", modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text("Adarsh Thakur", modifier = Modifier.fillMaxWidth(),
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp))
                        Text("House No 31,Sector 31 Panchkula Haryana,4050",Modifier.fillMaxWidth(),
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.caption.copy(fontSize = 11.sp))
                        Text("Pin Code: 43410",Modifier.fillMaxWidth(),
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.caption.copy(fontSize = 11.sp))

                    }

                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(shape = RoundedCornerShape(10.dp),
                elevation = 10.dp,
                contentColor = Color.White,
                backgroundColor = colorResource(MR.colors.backColor),
                modifier = Modifier.padding(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Image(painterResource(MR.images.packages_1),"", modifier = Modifier.size(23.dp))
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp),)
                    Text("Quantity", fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.h6.copy(fontSize = 14.sp))
                    Spacer(modifier = Modifier.weight(1f))
                    Text("10", fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.caption.copy(fontSize = 12.sp))
                }
            }
            Spacer(modifier = Modifier.height(9.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Scan QR code for payment", fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.h6.copy(fontSize = 14.sp))
                Spacer(modifier = Modifier.padding(vertical = 6.dp))
                Image(painterResource(MR.images.qr_code_1),"", modifier = Modifier.size(150.dp))
                Spacer(modifier = Modifier.padding(vertical = 6.dp))
                Text("Other Modes",
                    textAlign = TextAlign.Center,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

    }
    
}
@Composable
fun CustomRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp))
        Spacer(modifier = Modifier.weight(1f))
        Text(value,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.h6.copy(fontSize = 14.sp))
    }
}
@Composable
fun CustomRowBody(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.caption.copy(fontSize = 11.sp))
        Spacer(modifier = Modifier.weight(1f))
        Text(value,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.caption.copy(fontSize = 11.sp))
    }
}