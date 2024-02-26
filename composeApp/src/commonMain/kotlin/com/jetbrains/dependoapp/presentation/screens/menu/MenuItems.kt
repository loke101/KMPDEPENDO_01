package com.jetbrains.dependoapp.presentation.screens.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.painter.Painter
import com.icerockdev.library.MR
@Immutable
data object ScreensID {
    const val Home:String = "homeId"
    const val SendMoney="sendMoneyId"
    const val TransactionHistory="transHistoryId"
    const val CallLogs="callLogsId"
    const val MissCall="missCallId"
    const val Support="supportId"
    const val AboutUs="aboutUsId"
    const val Logout="logoutId"
}
@Immutable
data class MenuItems(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: Painter,
)


@Composable
fun getDrawerMenuItems(): List<MenuItems> {
    return listOf(
        MenuItems(
            id = ScreensID.Home,
            title = "Home",
            contentDescription = "Go to home screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.dashboard_1)
        ),
        MenuItems(
            id = ScreensID.SendMoney,
            title = "Send Money",
            contentDescription = "Go to send screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.send_money_1)
        ),
        MenuItems(
            id = ScreensID.TransactionHistory,
            title = "Transaction History",
            contentDescription = "Go to trans screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.file_1)
        ),
        MenuItems(
            id = ScreensID.CallLogs,
            title = "Call Logs",
            contentDescription = "Go to logs screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.call_logs_1)
        ),
        MenuItems(
            id = ScreensID.MissCall,
            title = "Missed Calls",
            contentDescription = "Go to missed screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.missed_call_1)
        ),
        MenuItems(
            id = ScreensID.Support,
            title = "Support",
            contentDescription = "Go to support screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.customer_support_1)
        ),
        MenuItems(
            id = ScreensID.AboutUs,
            title = "About Us",
            contentDescription = "Go to about screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.info_1)
        ),
        MenuItems(
            id = ScreensID.Logout,
            title = "Logout",
            contentDescription = "Go to logout screen",
            icon = dev.icerock.moko.resources.compose.painterResource(MR.images.logout_1)
        ),


        )
}
