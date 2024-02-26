package com.jetbrains.dependoapp.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icerockdev.library.MR
import com.jetbrains.dependoapp.presentation.screens.menu.MenuItems
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource


@Composable
fun DrawerHeader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(colorResource(MR.colors.backColor))
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Image(
            painterResource(MR.images.logo_1),
            contentDescription = "logo",
            modifier = Modifier
                .size(90.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))

        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = "Hi Lokendra",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItems>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(
        fontSize = 13.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.DarkGray
    ),
    onItemClick: (MenuItems) -> Unit,
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    painter = item.icon,
                    contentDescription = item.contentDescription,
                    tint = colorResource(MR.colors.backColor),
                    modifier = Modifier.size(width = 24.dp, height = 24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


