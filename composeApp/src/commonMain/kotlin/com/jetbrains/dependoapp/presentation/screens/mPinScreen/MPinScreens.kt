package com.jetbrains.dependoapp.presentation.screens.mPinScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.dependoapp.AppPref.AppPreferences
import com.jetbrains.dependoapp.presentation.screens.Home.HomeScreen
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import com.jetbrains.dependoapp.utils.PreferenceKeyConst.IS_MPIN_SET_KEY
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class MPinScreen(val mobile:String?=null):Screen, KoinComponent {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.current
        val mpinViewmodel: MpinViewmodel by inject<MpinViewmodel>()
        val appPreferences by inject<AppPreferences>()

        mpinViewmodel.getMobileNumber(mobile)
        MPinScreenMain(localNavigator,mpinViewmodel,appPreferences)
    }
}

@Composable
fun MPinScreenMain(
    localNavigator: Navigator?,
    mpinViewmodel: MpinViewmodel,
    appPreferences: AppPreferences,
) {
    val mpinScreenState by mpinViewmodel.mPINScreenState.collectAsState()
    var isMPINSets by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()


    LaunchedEffect(Unit){
        appPreferences.getBooleanValue(IS_MPIN_SET_KEY).collectLatest {
            if (it != null) {
                isMPINSets = it
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState,
        topBar = {
            TopBar(onNavigationIconClick = {
                localNavigator?.popAll()

            }, imageVector = Icons.Rounded.ArrowBack)
        },
        ) { paddingValues ->
        MpinScreenContent(
            modifier = Modifier.padding(paddingValues),
            onMPINButtonClicked = {
                if (!isMPINSets){
                    mpinViewmodel.onEvents(MPINEvents.onSetMpinClicked)
                }else{
                    mpinViewmodel.onEvents(MPINEvents.loginClicked)
                }

            },
            onInputValueChange = {
                mpinViewmodel.onLoginMPINInput(it)
            },
            inputValueLogin = mpinScreenState.mPinInput,
            inputValueReEntered = mpinScreenState.mPinInputReEntered,
            onInputValueChangeReEntered = {
                mpinViewmodel.onReEnteredInput(it)
            },
            isMpinSet = isMPINSets
        )
    }
    LaunchedEffect(mpinViewmodel.navigationEvents){
        mpinViewmodel.navigationEvents.collectLatest {
            when(it){
                is  MPINNavigateEvents.navigateToHome ->{
                    localNavigator?.push(HomeScreen())
                }
                else -> {}
            }

        }

    }
    LaunchedEffect(mpinViewmodel,mpinScreenState.errorMessage){
        if (mpinScreenState.errorMessage!=null){
            scaffoldState.snackbarHostState.showSnackbar(mpinScreenState.errorMessage.toString())
            mpinViewmodel.errorMessageReset()
        }
    }

}

@Composable
fun MpinScreenContent(
    modifier: Modifier = Modifier,
    onMPINButtonClicked:()->Unit,
    inputValueLogin: String,
    inputValueReEntered: String,
    onInputValueChange: (String) -> Unit,
    onInputValueChangeReEntered: (String) -> Unit,
    isMpinSet:Boolean?
) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp)

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(

                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        Modifier
                            .wrapContentSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            text = "Please Enter MPIN", fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.subtitle1
                        )
                        InputTextCustom(
                            inputValue = inputValueLogin,
                            onInputValueChange = {
                                onInputValueChange(it)
                            },
                            placeholderText = "Enter MPIN"
                        )
                        if (isMpinSet == false){
                            InputTextCustom(
                                inputValue = inputValueReEntered,
                                onInputValueChange = {
                                    onInputValueChangeReEntered(it)
                                },
                                placeholderText = "Re-Enter MPIN",
                                )
                        }
                        Text(
                            text = "Forget MPIN",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .clickable {},
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.caption,
                            color = Color.DarkGray,
                            textDecoration = TextDecoration.Underline
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                enabled = true,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                   onMPINButtonClicked()
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colors.onPrimary,
                                    disabledBackgroundColor = Color.LightGray,
                                ),
                            ) {
                                Text(text = if (isMpinSet==false) "Set MPIN" else "Login", fontFamily = FontFamily.SansSerif,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h6.copy(fontSize = 12.sp),
                                    modifier = Modifier.padding(3.dp).fillMaxWidth())
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }

                    }
                }

            }
        }

    }
}
@Composable
fun InputTextCustom(
    modifier: Modifier = Modifier,
    inputValue:String,
    onInputValueChange:(String)->Unit,
    placeholderText:String,
) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = {
            onInputValueChange(it)
        },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardActions = KeyboardActions(
            onDone = {
                // Handle the "Done" action if needed
            }
        ),
        maxLines = 6,
        minLines = 1,
        label = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.caption,
                fontFamily = FontFamily.SansSerif
            )
        },
        leadingIcon = {
            Icon(
                Icons.Rounded.Lock,
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = ""
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        singleLine = true,
    )

}