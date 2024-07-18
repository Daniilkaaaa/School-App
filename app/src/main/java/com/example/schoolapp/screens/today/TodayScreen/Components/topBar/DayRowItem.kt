package com.example.schoolapp.screens.today.TodayScreen.Components.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.screens.today.TodayScreen.Components.BadgeBox
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.firestore.DocumentReference

@Composable
fun DayRowItem(number: String, day: String, selectedDay: MutableState<Int>, startDay: Int, db: DocumentReference) {
    val assignments = remember { mutableStateOf(listOf<Assignments>()) }
    val lessons = remember { mutableStateListOf<Lesson>() }
    var countAssignments by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        fetchAssignments(db, assignments)
        fetchLessons(db, number.toInt(), lessons)
    }
    val matchedAssignments = assignments.value.filter { assignment ->
        println(assignment.classname)
        lessons.any { lesson -> lesson.name == assignment.classname }
    }
    countAssignments = matchedAssignments.size

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .width(50.dp)
            .height(70.dp)
            .background(
                if (number.toInt() == startDay) BlueColor else MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { selectedDay.value = number.toInt() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = number,
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.Bold,
            fontSize = 23.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = day,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surface,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        if (countAssignments > 0) {
            BadgeBox(countAssignments)
        }
    }
}

fun fetchAssignments(db: DocumentReference, assignments: MutableState<List<Assignments>>) {
    db.collection("assignments")
        .whereEqualTo("accepted", false)
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            val newAssignments = snapshots?.map { document ->
                Assignments(
                    title = document.getString("title") ?: "",
                    classname = document.getString("classname") ?: "",
                    details = document.getString("details") ?: "",
                    priority = document.getBoolean("priority") ?: false,
                    all_day = document.getBoolean("all_day") ?: false,
                    alert = document.getBoolean("alert") ?: false,
                    accepted = document.getBoolean("accepted") ?: false,
                )
            } ?: emptyList()
            assignments.value = newAssignments
        }
}

fun fetchLessons(db: DocumentReference, day: Int, lessons: SnapshotStateList<Lesson>) {
    db.collection("classes")
        .whereEqualTo("day", day)
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            lessons.clear()
            for (document in snapshots!!) {
                val lesson = document.toObject(Lesson::class.java)
                lessons.add(lesson)
            }
        }
}