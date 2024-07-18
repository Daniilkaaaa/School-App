package com.example.schoolapp.screens.settings.SettingsScreen.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Subject
import com.google.gson.Gson

@Composable
fun SubjectItem(navController: NavController, subject: Subject) {
    Row {
        val stringColor = "#" + subject.color
        val colorSubject = Color(android.graphics.Color.parseColor(stringColor))
        Divider(
            color = colorSubject,
            modifier = Modifier
                .padding(start = 10.dp, end = 5.dp)
                .width(3.dp)
                .height(20.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = subject.name,
            color = colorSubject,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )
        Row(
            modifier = Modifier
                .clickable {
                    val gson = Gson()
                    val jsonSubject = gson.toJson(subject)
                    navController.navigate("editing_class/$jsonSubject")
                }
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edit ",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 15.sp,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
    TabRowDefaults.Divider(
        color = androidx.compose.ui.graphics.Color.Gray,
        thickness = 1.dp,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}