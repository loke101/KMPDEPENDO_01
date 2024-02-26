package com.jetbrains.dependoapp.presentation.screens.Home


import com.jetbrains.dependoapp.Extenstions.navigateTo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR

import com.jetbrains.dependoapp.presentation.screens.FuelDetails.FuelDetailsScreenMain
import com.jetbrains.dependoapp.presentation.screens.components.DrawerBody
import com.jetbrains.dependoapp.presentation.screens.components.DrawerHeader
import com.jetbrains.dependoapp.presentation.screens.components.HomeBottomContent
import com.jetbrains.dependoapp.presentation.screens.components.HomeBoxContent
import com.jetbrains.dependoapp.presentation.screens.components.HomeTopContent
import com.jetbrains.dependoapp.presentation.screens.components.TopBar

import com.jetbrains.dependoapp.presentation.screens.location.GeoViewModel
import com.jetbrains.dependoapp.presentation.screens.menu.MenuItems
import com.jetbrains.dependoapp.presentation.screens.menu.getDrawerMenuItems
import com.jetbrains.dependoapp.presentation.screens.shipmets.ShipmentsScreen
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.LocationTrackerFactory
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreen() : Screen,KoinComponent{
    @Composable
    override fun Content() {
        val homeViewModel :HomeViewModel by inject<HomeViewModel>()
        val localNavigator = LocalNavigator.currentOrThrow
        val drawerItems  = getDrawerMenuItems()

        HomeMainScreen(homeViewModel,localNavigator,drawerItems)

    }

}


@Composable
fun HomeMainScreen(
    homeViewModel: HomeViewModel,
    localNavigator: Navigator,
    drawerItems: List<MenuItems>,
    ) {
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    HomeScreenContent(drawerItems, onShipmentBoxClick = { localNavigator.push(ShipmentsScreen())},
        navigateTo = { it.navigateTo(localNavigator) },
        onBottomFuelDetailsClick = {localNavigator.push(FuelDetailsScreenMain())},
        homeScreenState,
        scaffoldState,
        coroutineScope = scope

    )
}

@Composable
fun HomeScreenContent(
    drawerItems: List<MenuItems>,
    onShipmentBoxClick: () -> Unit,
    navigateTo: (MenuItems) -> Unit,
    onBottomFuelDetailsClick: () -> Unit,
    homeScreenState: HomeScreenState,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,


) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val locationTrackerFactory: LocationTrackerFactory = rememberLocationTrackerFactory(
        accuracy = LocationTrackerAccuracy.Best
    )
    val viewModel = getViewModel(
        key = "permissions-screen",
        factory = viewModelFactory {
            PermissionsViewModel(factory.createPermissionsController())
        }
    )

   val  geoViewModel = getViewModel(
        key = "geo-screen",
        factory = viewModelFactory {
            GeoViewModel(
                locationTracker = locationTrackerFactory.createLocationTracker()
            )
        }
    )
    BindLocationTrackerEffect(geoViewModel.locationTracker)
    BindEffect(viewModel.permissionsController)
    val text: State<String?> = geoViewModel.result.collectAsState()
    println("location update :${text.value}")



    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    coroutineScope.launch {
//                        onBottomFuelDetailsClick.invoke()
                        viewModel.onButtonClick()
                        geoViewModel.onStartClick()
                        geoViewModel.onStopClick()

                    }
                },
                text = { Text("Fuel") },
                icon = {
                    Icon(
                        painter = painterResource(MR.images.fuel_1),
                        contentDescription = "",
                        Modifier.size(24.dp),
                        tint = Color.White
                    )
                },
                backgroundColor = colorResource(MR.colors.blackOff),
                contentColor = Color.White,
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        topBar = {
            TopBar(onNavigationIconClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }

            }, imageVector = Icons.Rounded.Menu)
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody(items = (drawerItems), modifier = Modifier.width(290.dp)) {
                navigateTo(it)
            }
        }

    ) {

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .background(colorResource(MR.colors.backColor)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                //outer-card
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                ),
                elevation = 10.dp,
                backgroundColor = Color.White,

                ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Card(//inner-card
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        shape = RoundedCornerShape(18.dp),
                        elevation = 8.dp,
                        backgroundColor = colorResource(MR.colors.cardViewBack)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp)
                                .fillMaxWidth(),
                            text = "Shipment Metrics",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black,
                            fontFamily = FontFamily.SansSerif,
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),

                            ) {
                            Spacer(modifier = Modifier.height(50.dp))
                            HomeTopContent()
                            Spacer(modifier = Modifier.height(50.dp))
                            HomeBoxContent(onShipmentBoxClicked = {onShipmentBoxClick.invoke()},
                                unDeliveredShipmentCount = homeScreenState.undelCnt?:"0.0",
                                deliveredShipmentCount = homeScreenState.delCnt?:"0.0",
                                notAttemptShipmentCount = homeScreenState.unattemptCnt?:"0.0",
                                totalShipmentCount = homeScreenState.totalShipments?:"0.0"
                            )

                        }

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    HomeBottomContent(
                        cashTobeCollectedAmount = homeScreenState.remainingToPay?:"0.0",
                        todayCashDueAmount = homeScreenState.tdCodnotColtdAmt?:"0.0",
                        oldCashDueAmount = homeScreenState.prCodnotColtdAmt?:"0.0",
                        oldCashDepositCount = homeScreenState.prCodnotColtdCnt?:"0.0",
                        totalCashDueAmount = homeScreenState.totalAmount?:"0.0"
                    )
                }


            }


        }

    }
}

internal class PermissionsViewModel(
    val permissionsController: PermissionsController
) : ViewModel() {
    private val _state: MutableStateFlow<String> = MutableStateFlow("press button")
    val state: StateFlow<String> get() = _state

    fun onButtonClick() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.LOCATION)

                _state.value = "permission granted"
            } catch (exc: RequestCanceledException) {
                _state.value = "permission cancelled $exc"
            } catch (exc: DeniedException) {
                _state.value = "permission denied $exc"
            }
        }
    }
}