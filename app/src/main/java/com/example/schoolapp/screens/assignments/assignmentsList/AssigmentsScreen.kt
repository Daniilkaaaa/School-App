package com.example.schoolapp.screens.assignments.assignmentsList


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.dateTime.CurrentDateTime
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssignmentsScreen(navController: NavController, idUser: String) {
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.secondary)
        .fillMaxSize()) {
        Column {
            Text(
                text = "Assignments",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            SwitchableLists(idUser)
        }
        FloatingActionButton(
            onClick = { navController.navigate("creating_assigment") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .padding(20.dp),
            backgroundColor = BlueColor,
            shape = RoundedCornerShape(10)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SwitchableLists(idUser: String) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabItems = listOf("Due date", "Classes", "Priority")

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,) {
            OvalTabRow(
                items = tabItems,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                }
            )
        }
        val db = Firebase.firestore.collection("users").document(idUser)
        when (selectedTabIndex) {
            0 -> {
                Spacer(modifier = Modifier.height(32.dp))
                Text("Today-${CurrentDateTime()}",
                    color = MaterialTheme.colorScheme.tertiary, fontSize = 25.sp,modifier = Modifier.padding(start = 10.dp))
                val current = LocalDateTime.now()
                val year = current.year
                val month = current.monthValue
                val day = current.dayOfMonth
                ShowLessonAssignments(day, db)
                val plusDay = current.plusDays(1)
                Text("Tomorow-${TomorrowDate(plusDay)}", color = MaterialTheme.colorScheme.tertiary, fontSize = 25.sp, modifier = Modifier.padding(start = 10.dp, top = 15.dp))
                ShowLessonAssignments(plusDay.dayOfMonth, db)
            }
            1 -> {
                ShowClassesAssignments(db)

            }
            2 -> {
                ShowPriorityAssignments(db = db)
            }
        }
    }
}



@Composable
fun Item(db: DocumentReference, lesson: Lesson) {
    var expanded by remember { mutableStateOf(false)}
    val stringColor = "#" + lesson.color
    val colorSubject = Color(android.graphics.Color.parseColor(stringColor))
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
    assignments = assignments.filter { it.classname == lesson.name }
    Column(
        modifier = Modifier
            .padding(start = 10.dp, top = 15.dp, bottom = 15.dp)
    ) {
        Text(
            text = lesson.name,
            color = colorSubject,
            fontSize = 18.sp,
        )
        if (assignments.size > 0) {
            AssignmentsList(db, items = assignments, colorSubject)
        }
        else {
            Text(
                text = "No assignments",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 15.sp,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun TomorrowDate(tomorrow: LocalDateTime): String {
    val year = tomorrow.year
    val month = tomorrow.monthValue
    val day = tomorrow.dayOfMonth
    var nameMonth: String
    when(month) {
        1 -> nameMonth = "January"
        2 -> nameMonth = "February"
        3 -> nameMonth = "March"
        4 -> nameMonth = "April"
        5 -> nameMonth = "May"
        6 -> nameMonth = "June"
        7 -> nameMonth = "July"
        8 -> nameMonth = "August"
        9 -> nameMonth = "September"
        10 -> nameMonth = "October"
        11 -> nameMonth = "November"
        12 -> nameMonth = "December"
        else -> {
            nameMonth = "Ошибочка вышла:("
        }
    }
    val response = nameMonth + " " + day + "th, " + year
    return response
}

fun removeItem(item: String) {
    //todoItems = todoItems.filter { it != item }
}
