package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icerockdev.library.MR
import dev.icerock.moko.resources.compose.colorResource


@Composable
fun HomeBottomContent(
    cashTobeCollectedAmount:String?,
    todayCashDueAmount:String?,
    oldCashDueAmount:String?,
    oldCashDepositCount:String?,
    totalCashDueAmount:String?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
        ) {
            DashCashScreen(
                amount = "₹ ${cashTobeCollectedAmount?:"0.0"}",
                heading = "Cash to be collected",
                amountColor = Color.White
            )
            DashCashScreen(
                amount = "₹ ${todayCashDueAmount?:"0.0"}",
                heading = "Today's cash due for deposit",
                amountColor = Color.White
            )
            DashCashScreen(
                amount = "₹ ${oldCashDueAmount?:"0.0"}",
                heading = "Old cash due for deposit(${oldCashDepositCount?:"0.0"})",
                amountColor = Color.White
            )
        }
        AmountContent(
            amountText = "Total cash due for deposit",
            amountColor = Color.White,
            onClick = {},
            amountHeading = "₹ ${totalCashDueAmount?:"0.0"}",

            )
    }

}

@Composable
fun AmountContent(
    amountHeading: String? = "",
    amountText: String? = "",
    amountColor: Color?,
    onClick: (() -> Unit)? = null,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp,
        backgroundColor = colorResource(MR.colors.backColor),
        contentColor = Color.White,
        modifier = Modifier
            .padding(start = 1.dp, end = 1.dp)
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
    ) {
        Row(
            Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = amountText.toString(),
                modifier = Modifier.padding(start = 3.dp),
                color = colorResource(MR.colors.cardViewBack),
                textDecoration = TextDecoration.Underline,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (amountColor != null) {
                    Text(
                        text = amountHeading.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        color = amountColor,
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow Icon",
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )

            }

        }
    }


}


@Composable
fun DashCashScreen(
    amount: String,
    heading: String,
    amountColor: Color,
) {
    Card(modifier = Modifier.size(width = 105.dp, height = 70.dp),
        elevation = 10.dp,
        contentColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary) {
        Column(verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            Text(text = "Rs $amount", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif, style = MaterialTheme.typography.caption)
            Text(text = heading,Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontFamily = FontFamily.SansSerif,
                style = MaterialTheme.typography.caption
                )

        }
    }
}

fun addLineBreaks(text: String, wordsPerLine: Int): String {
    val words = text.split(Regex("\\s+"))
    val modifiedWords = words.chunked(wordsPerLine) { it.joinToString(" ") }
    return modifiedWords.joinToString("\n")
}