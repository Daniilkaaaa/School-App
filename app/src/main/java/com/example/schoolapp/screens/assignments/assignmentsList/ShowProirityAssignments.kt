package com.example.schoolapp.screens.assignments.assignmentsList


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.data.Subject
import com.example.schoolapp.screens.today.TodayScreen.Components.updateAssigmentsAccepted
import com.google.firebase.firestore.DocumentReference

@Composable
fun ShowPriorityAssignments(db: DocumentReference) {
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
    assignments = assignments.filter { it.priority == true }
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

@Composable
fun AssignmentsList(db: DocumentReference, items: List<Assignments>, color: androidx.compose.ui.graphics.Color) {
    Column {
        if (items.size == 0) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "No assignments",
                color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                fontSize = 23.sp,
            )
        }
        else {
            items.forEach { item ->
                val acceptedState = remember { mutableStateOf(item.accepted) }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = acceptedState.value,
                        colors = CheckboxDefaults.colors(
                            checkedColor = color,
                            checkmarkColor = MaterialTheme.colorScheme.tertiary,
                            uncheckedColor = MaterialTheme.colorScheme.tertiary
                        ),
                        onCheckedChange = { isChecked ->
                            acceptedState.value = isChecked
                            updateAssigmentsAccepted(db, item, isChecked)
                        }
                    )
                    Text(
                        text = item.title,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 18.sp,
                        textDecoration = if (acceptedState.value) TextDecoration.LineThrough else null,
                    )
                }
            }
        }
    }
}