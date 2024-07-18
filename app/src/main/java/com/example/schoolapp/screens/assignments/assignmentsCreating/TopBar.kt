package com.example.schoolapp.screens.assignments.assignmentsCreating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.firestore.DocumentReference

@Composable
fun TopBar(title: String, className: String, details: String, priority: Boolean, allDays: Boolean, alert: Boolean, db: DocumentReference) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        //.background(BackgroundDarkest2),
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

                    }
            )

            Text(
                text = "Save",
                fontSize = 20.sp,
                color = BlueColor,
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .clickable {
                        if (title != "") {
                            val assigment = Assignments(title, className, details, priority, allDays, alert, false)
                            db.collection("assignments").document().set(assigment)
                        }
                    }
            )
        }
    }
}