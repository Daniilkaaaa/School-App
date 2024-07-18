package com.example.schoolapp.screens.lessonEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun TopBarCreateLesson(navController: NavController, name: String, shortName: String, color: String, room: Int, year: Int, month: Int, day: Int, duration: String, idUser: String, id: String) {
    val db = Firebase.firestore.collection("users").document(idUser)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ){
        Row {
            Text(
                text = "Cansel",
                fontSize = 20.sp,
                color = BlueColor,
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp)
                    .weight(1f)
                    .clickable {
                        navController.navigate("settings")
                    }
            )

            Text(
                text = "Save",
                fontSize = 20.sp,
                color = BlueColor,
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .clickable {
                        val times = duration.split("-").map { it.trim() }
                        val beginning = times[0]
                        val end = times[1]
                        db.collection("classes").document(id).update(mapOf(
                            "name" to name,
                            "shortName" to shortName,
                            "color" to color,
                            "room" to room,
                            "year" to year,
                            "month" to month,
                            "day" to day,
                            "begining" to beginning,
                            "end" to end
                        ))
                        navController.navigate("today")
                    }
            )
        }
    }
}