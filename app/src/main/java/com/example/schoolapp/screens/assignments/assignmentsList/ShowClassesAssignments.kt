package com.example.schoolapp.screens.assignments.assignmentsList

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.data.Subject
import com.google.firebase.firestore.DocumentReference

@Composable
fun ShowClassesAssignments(db: DocumentReference) {
    val subjects = remember { mutableStateListOf<Subject>() }
    LaunchedEffect(Unit) {
        db.collection("subjects")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                subjects.clear()
                for (document in snapshots!!) {
                    val subject = document.toObject(Subject::class.java)
                    subjects.add(subject)
                }
            }
    }
    var assignments by remember { mutableStateOf(listOf<Assignments>()) }
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
    }
    if (assignments.size > 0) {
        subjects.forEach{
            Spacer(modifier = Modifier.height(30.dp))
            val subject = it
            val stringColor = "#" + subject.color
            val colorSubject = Color(android.graphics.Color.parseColor(stringColor))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = subject.name,
                color = colorSubject,
                fontSize = 23.sp,
            )
            AssignmentsList(db = db, items = assignments.filter {it.classname == subject.name}, colorSubject)
        }
    }
    else {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "No assignments",
            color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
            fontSize = 23.sp,
        )
    }



}