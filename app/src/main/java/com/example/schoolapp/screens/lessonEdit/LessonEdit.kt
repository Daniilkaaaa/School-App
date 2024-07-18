package com.example.schoolapp.screens.lessonEdit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.data.Subject
import com.example.schoolapp.screens.lessonEdit.Components.ChangeClass
import com.example.schoolapp.screens.lessonEdit.Components.ChangeDuration
import com.example.schoolapp.screens.lessonEdit.Components.TopBarCreateLesson
import com.example.schoolapp.ui.theme.TextColorGrey
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonEditingScreen(navController: NavController,lesson: Lesson, idUser: String) {
    val db = Firebase.firestore.collection("users").document(idUser)
    var id by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        fetchLessonId(db, lesson) { fetchedId ->
            id = fetchedId
        }
    }
    var subject by remember { mutableStateOf(Subject(lesson.name, lesson.shortName, lesson.color)) }
    var room by remember { mutableStateOf(lesson.room) }
    var year by remember { mutableStateOf(lesson.year) }
    var month by remember { mutableStateOf(lesson.month) }
    var day by remember { mutableStateOf(lesson.day) }
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
            idUser = idUser,
            id = id
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

fun fetchLessonId(db: DocumentReference, lesson: Lesson, onIdFetched: (String) -> Unit) {
    db.collection("classes")
        .whereEqualTo("color", lesson.color)
        .whereEqualTo("name", lesson.name)
        .whereEqualTo("shortName", lesson.shortName)
        .whereEqualTo("room", lesson.room)
        .whereEqualTo("year", lesson.year)
        .whereEqualTo("month", lesson.month)
        .whereEqualTo("day", lesson.day)
        .whereEqualTo("begining", lesson.begining)
        .whereEqualTo("end", lesson.end)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                if (document["name"] == lesson.name) {
                    onIdFetched(document.id)
                    return@addOnSuccessListener
                }
            }
        }
}
