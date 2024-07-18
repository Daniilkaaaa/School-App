package com.example.schoolapp.screens.settings.SettingsScreen.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddLessonButton(navController: NavController) {
    Spacer(Modifier.height(20.dp))
    Text(
        text = "Add lesson",
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(start = 10.dp, top = 15.dp, end = 10.dp)
            .clickable {
                navController.navigate("creating_lesson")
            }
    )
    TabRowDefaults.Divider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}