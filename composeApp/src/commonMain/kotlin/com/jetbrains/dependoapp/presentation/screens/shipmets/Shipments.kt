package com.jetbrains.dependoapp.presentation.screens.shipmets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.CallingDetails.CallScreenMain
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import com.jetbrains.dependoapp.presentation.screens.shipmentDetails.AirwayBillDetailsScreen
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource

class ShipmentsScreen:Screen {
    @Composable
    override fun Content() {
        val navigator= LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                ShipmentListMain(navigateToDetailsScreen = {
                    navigator.push(AirwayBillDetailsScreen())
                }, navigateToCallScreen = {
                    navigator.push(CallScreenMain())
                })
            })
    }

}

@Composable
fun ShipmentListMain(navigateToDetailsScreen:()->Unit,navigateToCallScreen:()->Unit) {
    ShipmentListContent(onAirBillClick = { navigateToDetailsScreen() }, onCallClick = {
        navigateToCallScreen.invoke()
    })

}

@Composable
private fun ShipmentListContent(onAirBillClick:()->Unit, onCallClick:()->Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, end = 10.dp, start = 10.dp, bottom = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(MR.images.person_shipment_1),
                                "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp),
                                colorFilter = ColorFilter.tint(colorResource(MR.colors.backColor))
                            )
                            Text(
                                "Adarsh",
                                fontFamily = FontFamily.SansSerif,
                                style = MaterialTheme.typography.body2
                            )
                            Image(
                                painterResource(MR.images.phone_call_2),
                                "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp).clickable {
                                    onCallClick()
                                }
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Change Status",
                                style = MaterialTheme.typography.body2,
                                fontFamily = FontFamily.SansSerif,
                                textDecoration = TextDecoration.Underline
                            )
                            Image(
                                painterResource(MR.images.editor_1), "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(
                            painterResource(MR.images.file_awb_2),
                            "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(colorResource(MR.colors.backColor))
                        )
                        Text(
                            "CC001064646",
                            style = MaterialTheme.typography.body2,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.clickable {
                                onAirBillClick.invoke()
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "Delivered",
                            style = MaterialTheme.typography.body2,
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(MR.colors.deliveredEndColor)
                        )

                    }

                }

        }

    }

}

