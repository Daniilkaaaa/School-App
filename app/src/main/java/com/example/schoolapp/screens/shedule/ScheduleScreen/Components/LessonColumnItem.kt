package com.example.schoolapp.screens.shedule.ScheduleScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolapp.data.Lesson

@Composable
fun LessonColumnItem(lesson: Lesson, padding: Int) {
    val stringColor = "#" + lesson.color
    val colorSubject = Color(android.graphics.Color.parseColor(stringColor))
    val room = lesson.room
    Spacer(modifier = Modifier.height(padding.dp))
    Box(
        modifier = Modifier
            .background(colorSubject, shape = RoundedCornerShape(8.dp))
            .width(65.dp)
            .height(65.dp)
    ) {
        Column() {
            Text(
                text = lesson.shortName,
                color = androidx.compose.ui.graphics.Color.Black,
                fontSize = 15.sp,
            )
            Text(
                text = "Room: $room",
                color = androidx.compose.ui.graphics.Color.Black,
                fontSize = 12.sp,
            )
        }
    }
}