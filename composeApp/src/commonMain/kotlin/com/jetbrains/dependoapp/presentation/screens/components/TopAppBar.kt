package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.icerockdev.library.MR
import dev.icerock.moko.resources.compose.colorResource

@Composable
fun TopBar(
    imageVector: ImageVector,
    onNavigationIconClick: () -> Unit,

) {
    androidx.compose.material.TopAppBar(
        title = { Text("Dependo") },
        backgroundColor = colorResource(MR.colors.backColor),
        navigationIcon = {
            IconButton(
                modifier = Modifier.size(50.dp),
                onClick = { onNavigationIconClick.invoke() }) {
                Icon(
                    imageVector,
                    contentDescription = "",
                    tint = Color.White
                )

            }

        },
        contentColor = Color.White
    )

}