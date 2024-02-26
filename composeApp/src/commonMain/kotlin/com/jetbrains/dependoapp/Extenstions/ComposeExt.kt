package com.jetbrains.dependoapp.Extenstions

import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.dependoapp.presentation.screens.About.AboutUsScreenMain
import com.jetbrains.dependoapp.presentation.screens.CallLogs.CallLogsScreenMain
import com.jetbrains.dependoapp.presentation.screens.EndKmDetails.EndKmScreenMain
import com.jetbrains.dependoapp.presentation.screens.MissedCalls.MissCallScreenMain
import com.jetbrains.dependoapp.presentation.screens.SendMoneyScreen.SendMoneyScreenMain
import com.jetbrains.dependoapp.presentation.screens.Support.SupportScreenMain
import com.jetbrains.dependoapp.presentation.screens.Transactions.TransactionScreenMain
import com.jetbrains.dependoapp.presentation.screens.menu.MenuItems
import com.jetbrains.dependoapp.presentation.screens.menu.ScreensID


fun MenuItems.navigateTo(navigator: Navigator) {
    when (this.id) {
        ScreensID.Home ->{  }
        ScreensID.SendMoney -> {navigator.push(SendMoneyScreenMain())}
        ScreensID.TransactionHistory->{navigator.push(TransactionScreenMain())}
        ScreensID.CallLogs->{navigator.push(CallLogsScreenMain())}
        ScreensID.MissCall->{navigator.push(MissCallScreenMain())}
        ScreensID.Support->{navigator.push(SupportScreenMain())}
        ScreensID.AboutUs->{navigator.push(AboutUsScreenMain())}
        ScreensID.Logout->{navigator.push(EndKmScreenMain())}
    }
}