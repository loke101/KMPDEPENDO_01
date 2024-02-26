package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import dev.icerock.moko.resources.compose.colorResource

class TransactionDetailsScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                TransactionDetailsScreen()
            })

    }

}

@Composable
private fun TransactionDetailsScreen() {
    TransactionScreenDetailsContent()
}

@Composable
private fun TransactionScreenDetailsContent() {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth().padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.DarkGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "MO",
                    color = Color.White,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )

            }
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 9.dp),
                text = "MOBILE2WIN",
                color = Color.DarkGray,
                style = MaterialTheme.typography.caption,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "₹ 2",
                color = colorResource(MR.colors.backColor),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 33.sp
                ),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(colorResource(MR.colors.deliveredEndColor))
                    .padding(5.dp),
                text = "Success",
                color = Color.White,
                style = MaterialTheme.typography.caption,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth().padding(top = 9.dp)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground),
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 9.dp),
                text = "Jan 29 2024 12:56 pm",
                color = colorResource(MR.colors.blackOff),
                style = MaterialTheme.typography.caption,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                elevation = 9.dp,
                backgroundColor = Color.White,
                contentColor = Color.DarkGray
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Dependo Transaction ID",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "240504040403030303",
                        color = Color.Black.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "UPI Transaction ID:",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "282883883383883833",
                        color = Color.Black.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "To:",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "VPA: depndo@indus",
                        color = Color.Black.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "From:",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "VPA: OkAxsis@okhdfcbank",
                        color = Color.Black.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = 9.dp,
                backgroundColor = Color.White,
                contentColor = Color.DarkGray
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Shipment List (2)",
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.h6.copy(fontSize = 13.sp),
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Start
                        )
                        Image(
                            Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    isExpanded.value = !isExpanded.value
                                }
                        )
                    }
                    AnimatedVisibility(
                        visible = isExpanded.value,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "CC01818181",
                                color = Color.Black.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.caption,
                                fontFamily = FontFamily.SansSerif,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "₹ 10",
                                color = colorResource(MR.colors.deliveredEndColor),
                                style = MaterialTheme.typography.caption,
                                fontFamily = FontFamily.SansSerif,
                                textAlign = TextAlign.Start
                            )

                        }


                    }


                }

            }
        }

    }

}
