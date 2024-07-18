package com.example.schoolapp.screens.assignments.assignmentsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.schoolapp.data.Lesson
import com.google.firebase.firestore.DocumentReference

@Composable
fun ShowLessonAssignments(day: Int, db: DocumentReference) {
    val lessons = remember { mutableStateListOf<Lesson>() }
    LaunchedEffect(Unit) {
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
            }
    }
    val filteredLessons = lessons.filter { lesson ->
        lesson.day == day
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filteredLessons) { lesson ->
            Item(db, lesson)
        }
    }
}