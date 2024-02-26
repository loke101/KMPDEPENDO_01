package com.jetbrains.dependoapp.presentation.screens.FuelDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.components.TopBar
import dev.icerock.moko.resources.compose.painterResource


class FuelDetailsScreenMain :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(Icons.Rounded.ArrowBack, onNavigationIconClick = {navigator.pop()}) },
            content = {
                FuelDetailsScreen()
            })
    }
    }

@Composable
private fun FuelDetailsScreen() {
    var isExpanded by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf("LMD") }
    var kmText by remember { mutableStateOf("") }
    FuelDetailsScreenContent(
        isExpanded = isExpanded,
        mode = mode,
        kmText = kmText,
        onExpand = {
             isExpanded = it
        },
        onStartKmChange = {
             kmText = it
        },
        onModeChange = {
             mode = it
        },
        onDismiss = {
            isExpanded = false
        })

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FuelDetailsScreenContent(
    isExpanded:Boolean,
    mode:String,
    kmText:String,
    onExpand:(Boolean)->Unit,
    onStartKmChange:(String)->Unit,
    onModeChange:(String)->Unit,
    onDismiss:()->Unit

) {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                onExpand(it)
            }
        ) {
            TextField(
                value = mode,
                onValueChange = {
                    onModeChange(it)
                                },
                readOnly = true,
                shape = TextFieldDefaults.OutlinedTextFieldShape,
                textStyle = MaterialTheme.typography.body2,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(textColor = Color.DarkGray, unfocusedBorderColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                onDismissRequest = {
                    onDismiss.invoke()

                }) {
                DropdownMenuItem(
                    onClick = {
                        onExpand(false)
                        onModeChange("LMD")
                    },
                    content = {
                        Text("LMD")
                    }

                )

            }

        }
        TextField(
            value = kmText,
            onValueChange = {
                 onStartKmChange(it)
            },
            Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(
                    text = "Enter Start Km",
                    style = MaterialTheme.typography.caption,
                    fontFamily = FontFamily.SansSerif
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            shape = TextFieldDefaults.TextFieldShape,
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(200.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(200.dp).align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(MR.images.cam_1),
                contentDescription = "fuel",
                modifier = Modifier
                    .size(40.dp)
                    .fillMaxWidth()
                    .clickable {},
                contentScale = ContentScale.Crop,
            )
            Text(text = "Click to capture")
        }
    }
}