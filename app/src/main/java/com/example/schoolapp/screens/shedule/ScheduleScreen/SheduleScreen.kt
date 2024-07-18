package com.example.schoolapp.screens.shedule.ScheduleScreen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.screens.shedule.ScheduleScreen.Components.Header
import com.example.schoolapp.screens.shedule.ScheduleScreen.Components.ScheduleContent
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(idUser: String) {
    val lessons = remember { mutableStateListOf<Lesson>() }
    val db = Firebase.firestore.collection("users").document(idUser)

    LaunchedEffect(Unit) {
        fetchLessons(db, lessons)
    }

    val calendarWeek = generateCalendarWeek()
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.secondary)
        .fillMaxHeight()) {
        Column {
            Header()
            ScheduleContent(lessons, calendarWeek)
        }
    }
}


fun fetchLessons(db: DocumentReference, lessons: MutableList<Lesson>) {
    db.collection("classes")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            lessons.clear()
            for (document in snapshots!!) {
                val lesson = document.toObject(Lesson::class.java)
                lessons.add(lesson)
            }
            lessons.sortBy { it.end }
        }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateCalendarWeek(): List<Pair<String, String>> {
    val current = LocalDateTime.now()
    var currentDate = LocalDate.of(current.year, current.monthValue, current.dayOfMonth).minusDays(7)
    val days = mutableListOf<Pair<String, String>>()

    for (i in 1..14) {
        days.add(Pair(currentDate.dayOfMonth.toString(), currentDate.dayOfWeek.getDisplayName(
            TextStyle.SHORT, Locale.getDefault())))
        currentDate = currentDate.plusDays(1)
    }

    val offset = OffsetDays(days[7].second)
    val (firstId, secondId) = if (offset.second > 4) {
        if (offset.second == 6) 2 to 6 else 1 to 5
    } else {
        7 - offset.first to 7 + offset.second
    }

    return days.slice(firstId..secondId)
}

@Composable
fun HoursTable(hour: Int) {
    Box(modifier = Modifier.fillMaxWidth().height(65.dp)) {
        Text(
            text = hour.toString(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 17.sp,
        )
    }
}

fun OffsetDays(dayOfWeek: String): Pair<Int,Int>  {
    when (dayOfWeek) {
        "Mon" -> return Pair(0, 4)
        "Tue" -> return Pair(1, 3)
        "Wed" -> return Pair(2, 2)
        "Thu" -> return Pair(3, 1)
        "Fri" -> return Pair(4, 0)
        "Sat" -> return Pair(0, 6)
        "Sun" -> return Pair(0, 5)
        else -> return Pair(0, 0)
    }
}








