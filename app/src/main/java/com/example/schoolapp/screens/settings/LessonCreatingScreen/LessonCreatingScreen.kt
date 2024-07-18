package com.example.schoolapp.screens.settings.LessonCreatingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.data.Subject
import com.example.schoolapp.screens.settings.LessonCreatingScreen.Components.ChangeClass
import com.example.schoolapp.screens.settings.LessonCreatingScreen.Components.ChangeDuration
import com.example.schoolapp.screens.settings.LessonCreatingScreen.Components.TopBarCreateLesson
import com.example.schoolapp.ui.theme.BlueColor
import com.example.schoolapp.ui.theme.TextColorGrey
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonCreatingScreen(navController: NavController, idUser: String) {
    val db = Firebase.firestore.collection("users").document(idUser)
    var subject by remember { mutableStateOf(Subject("","", "")) }
    var room by remember { mutableStateOf(0) }
    var year by remember { mutableStateOf(0) }
    var month by remember { mutableStateOf(0) }
    var day by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf("") }
    var beginning by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }

    Column(
    ) {
        TopBarCreateLesson(
            navController = navController,
            name = subject.name,
            shortName = subject.shortName,
            color = subject.color,
            room = room,
            year = year,
            month = month,
            day = day,
            duration = duration,
            idUser = idUser
        )
        ChangeClass(db){selectedClass ->
            subject = selectedClass
        }

        Text(
            text = "Room",
            fontSize = 20.sp,
            color = TextColorGrey,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)

        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),

            value = room.toString(),
            onValueChange = { room = it.toIntOrNull() ?: 0 },
        )
        Text(
            text = "Year",
            fontSize = 20.sp,
            color = TextColorGrey,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)

        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),

            value = year.toString(),
            onValueChange = { year = it.toIntOrNull() ?: 0 },
        )
        Text(
            text = "Month",
            fontSize = 20.sp,
            color = TextColorGrey,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)

        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),

            value = month.toString(),
            onValueChange = { month = it.toIntOrNull() ?: 0 },
        )

        Text(
            text = "Day",
            fontSize = 20.sp,
            color = TextColorGrey,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)

        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),

            value = day.toString(),
            onValueChange = { day = it.toIntOrNull() ?: 0 },
        )

        Text(
            text = "Duration",
            fontSize = 20.sp,
            color = TextColorGrey,
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)

        )

        ChangeDuration {selectedDuration ->
            duration = selectedDuration
        }

    }
}