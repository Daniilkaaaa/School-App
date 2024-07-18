package com.example.schoolapp.screens.shedule.ScheduleScreen.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.screens.shedule.ScheduleScreen.HoursTable

@Composable
fun ScheduleContent(lessons: List<Lesson>, calendarWeek: List<Pair<String, String>>) {
    Row {
        LazyColumn(modifier = Modifier.width(40.dp).padding(top = 100.dp)) {
            items((8..16).toList()) { hour ->
                HoursTable(hour = hour)
            }
        }
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 20.dp)) {
            items(calendarWeek) { (number, day) ->
                DayColumn(lessons, number, day)
            }
        }
    }
}