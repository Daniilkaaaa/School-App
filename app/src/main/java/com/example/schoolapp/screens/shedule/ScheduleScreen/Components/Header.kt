package com.example.schoolapp.screens.shedule.ScheduleScreen.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header() {
    Text(
        text = "Schedule",
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(top = 30.dp, start = 10.dp)
    )
}