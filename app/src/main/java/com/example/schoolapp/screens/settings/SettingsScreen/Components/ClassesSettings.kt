package com.example.schoolapp.screens.settings.SettingsScreen.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Subject

@Composable
fun ClassesSettings(navController: NavController, subjects: List<Subject>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text(
            text = "Classes settings",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)
                .weight(1f)
        )
        Text(
            text = "+ class",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(top = 15.dp, end = 10.dp)
                .clickable {
                    navController.navigate("creating_class")
                }
        )
    }
    Spacer(Modifier.height(20.dp))
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(subjects) { subject ->
            SubjectItem(navController, subject)
        }
    }
}