package com.example.schoolapp.screens.today.TodayScreen.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.TimeUtils.formatDuration
import androidx.navigation.NavController
import com.example.schoolapp.R
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.firestore.DocumentReference
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LessonContainer(navController: NavController, lesson: Lesson, db: DocumentReference) {
    var expanded by remember { mutableStateOf(false) }
    val colorSubject = Color(android.graphics.Color.parseColor("#${lesson.color}"))
    var unacceptedAssignmentsCount by remember { mutableStateOf(0) }
    var assignments by remember { mutableStateOf(listOf<Assignments>()) }

    LaunchedEffect(Unit) {
        db.collection("assignments").get().addOnSuccessListener { result ->
            assignments = result.map { document ->
                Assignments(
                    title = document.getString("title") ?: "",
                    classname = document.getString("classname") ?: "",
                    details = document.getString("details") ?: "",
                    priority = document.getBoolean("priority") ?: false,
                    all_day = document.getBoolean("all_day") ?: false,
                    alert = document.getBoolean("alert") ?: false,
                    accepted = document.getBoolean("accepted") ?: false,
                )
            }
            assignments = assignments.filter { it.classname == lesson.name }
            unacceptedAssignmentsCount = assignments.count { !it.accepted }
        }
    }

    val assignmentsQuery = db.collection("assignments")
        .whereEqualTo("classname", lesson.name)
        .whereEqualTo("accepted", false)

    DisposableEffect(Unit) {
        val listenerRegistration = assignmentsQuery.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            unacceptedAssignmentsCount = snapshot?.size() ?: 0
        }
        onDispose { listenerRegistration.remove() }
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { expanded = !expanded }
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(end = 20.dp)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = if (expanded) "Скрыть" else "Показать",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 5.dp, top = 5.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp, top = 15.dp, bottom = 15.dp)
            ) {
                Text(
                    text = lesson.begining,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = lesson.end,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Divider(
                color = colorSubject,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(3.dp)
                    .height(50.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, top = 15.dp, bottom = 15.dp)
            ) {
                Row(modifier = Modifier.align(Alignment.Start)) {
                    Column(modifier = Modifier
                        .width(200.dp)
                        .weight(1f)) {
                        Text(
                            text = lesson.name,
                            color = colorSubject,
                            fontSize = 20.sp,
                        )
                    }
                    if (expanded == false)
                    {
                        RemainingTime(lesson)
                    }
                    else {
                        Icon(painter = painterResource(R.drawable.settings),
                            contentDescription = null,
                            tint = colorSubject,
                            modifier = Modifier.clickable {
                                val gson = Gson()
                                val jsonLesson = gson.toJson(lesson)
                                navController.navigate("editing_lesson/$jsonLesson")
                            })
                    }
                }
                Spacer(modifier = Modifier
                    .height(5.dp)
                    .padding(top = 5.dp, end = 5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = lesson.room.toString(), fontSize = 15.sp, color = MaterialTheme.colorScheme.surface)
                    Spacer(modifier = Modifier.width(5.dp))
                    if (unacceptedAssignmentsCount > 0) {
                        BadgeBox(unacceptedAssignmentsCount)
                        Text(text = "Missing assignment:", fontSize = 15.sp, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text(
                            text = "Assignments",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(top = 5.dp)
                        )
                        fun removeItem(item: String) {
                            //todoItems = todoItems.filter { it != item }
                        }
                        TodoList(db, items = assignments, onRemoveItem = ::removeItem, colorSubject)
                        Row {
                            Button(
                                modifier = Modifier.size(100.dp, 40.dp),
                                onClick = { expanded = !expanded },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                border = BorderStroke(1.dp, BlueColor),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                Text(text = "Cancel", fontSize = 12.sp, color = MaterialTheme.colorScheme.tertiary)
                            }
                            Button(
                                modifier = Modifier
                                    .size(130.dp, 40.dp)
                                    .padding(start = 8.dp),
                                onClick = { navController.navigate("creating_assigment") },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(BlueColor),
                                border = BorderStroke(1.dp, BlueColor),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                Text(text = "+ Assigned", fontSize = 12.sp, color = androidx.compose.ui.graphics.Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun RemainingTime(lesson: Lesson) {
    val (beginHour, beginMinute) = lesson.begining.split(":").map { it.toInt() }
    val beginingLesson = LocalDateTime.of(lesson.year, lesson.month, lesson.day, beginHour, beginMinute, 0)
    val (endHour, endMinute) = lesson.end.split(":").map { it.toInt() }
    val endLesson = LocalDateTime.of(lesson.year, lesson.month, lesson.day, endHour, endMinute, 0)
    var now = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
    println(now.hour.toString())
    if (isDateTimeBetween(now, beginingLesson, endLesson)) {
        println("now")
        Text("now", color = androidx.compose.ui.graphics.Color.White, fontSize = 13.sp,)
    }
    else if (now.isBefore(beginingLesson)) {
        println("countdown")
        CountdownTimer(beginingLesson)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun isDateTimeBetween(targetDateTime: LocalDateTime, startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean {
    return targetDateTime.isAfter(startDateTime) && targetDateTime.isBefore(endDateTime)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CountdownTimer(targetTime: LocalDateTime) {
    var remainingTime by remember { mutableStateOf(java.time.Duration.between(LocalDateTime.now(), targetTime)) }
    LaunchedEffect(Unit) {
        while (remainingTime > java.time.Duration.ZERO) {
            delay(1000L)
            remainingTime = java.time.Duration.between(LocalDateTime.now(), targetTime)
        }
    }
    formatDuration(remainingTime)

}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun formatDuration(duration: java.time.Duration) {
    val hours = (duration.toHours() - 3)
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60
    if (hours <= 1) {
        Text(
            text = String.format("in %02dmin",minutes),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}