package com.example.schoolapp.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.data.Subject
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ChangeClass(db: DocumentReference, onClassSelected: (Subject) -> Unit) {
    var subjects by remember { mutableStateOf(listOf<Subject>()) }
    db.collection("subjects").get().addOnSuccessListener { result ->
        subjects = result.map { document ->
            Subject(
                name = document.getString("name") ?: "",
                shortName = document.getString("shortName") ?: "",
                color = document.getString("color") ?: ""
            )
        }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }

    androidx.compose.material3.ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(top = 25.dp)
    ) {
        OutlinedTextField(
            value = subjects.getOrNull(selectedItemIndex)?.name ?: "",
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            subjects.forEachIndexed { index, subject ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = subject.name,
                            fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                        )
                    },
                    onClick = {
                        selectedItemIndex = index
                        onClassSelected(subject)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeDuration(onDurationSelected: (String) -> Unit) {
    val context = LocalContext.current
    val durations = arrayOf("08:00-08:40", "08:50-09:30", "09:40-10:20", "10:30-11:10", "11:40-12:20",
        "12:30-13:10", "13:20-14:00", "14:10-14:50", "15:40-16:10", "16:20-17:00")
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }

    androidx.compose.material3.ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
    ) {
        OutlinedTextField(
            value = durations[selectedItemIndex], // Установка выбранного значения
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(60.dp)
                .padding(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            durations.forEachIndexed { index, duration ->
                DropdownMenuItem(
                    text = { Text(text = duration) },
                    onClick = {
                        selectedItemIndex = index
                        onDurationSelected(duration) // Обновление выбранного значения
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TopBarCreateLesson(navController: NavController, name: String, shortName: String, color: String,room: Int,year: Int, month: Int, day: Int, duration: String, idUser: String) {
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
                        val lesson = Lesson(name, shortName, color, room,year, month, day, beginning, end)
                        db.collection("classes").document().set(lesson)
                        navController.navigate("settings")
                    }
            )
        }
    }
}