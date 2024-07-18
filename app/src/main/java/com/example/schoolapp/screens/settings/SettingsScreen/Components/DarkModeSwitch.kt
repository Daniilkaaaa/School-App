package com.example.schoolapp.screens.settings.SettingsScreen.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.ui.theme.BlueColor
import com.example.schoolapp.ui.theme.TextColorGrey

@Composable
fun DarkModeSwitch(isDarkTheme: MutableState<Boolean>, toggleTheme: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 10.dp, top = 25.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Dark mode",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 20.sp
        )
        Switch(
            checked = isDarkTheme.value,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White,
                checkedTrackColor = BlueColor,
                uncheckedTrackColor = TextColorGrey
            ),
            onCheckedChange = { toggleTheme() }
        )
    }
}