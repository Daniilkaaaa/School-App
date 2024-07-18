package com.example.schoolapp.screens.shedule.ScheduleScreen.Components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.schoolapp.data.Lesson

@Composable
fun LessonColumn(lessons: List<Lesson>, day: Int) {
    var startHour = 8
    var paddingTop = 0
    var flag = false
    var filteredLessons = lessons.filter { it.day == day }
    filteredLessons = filteredLessons.sortedBy { it.end }
    var paddingsTop = mutableListOf<Int>()
    if (filteredLessons.size > 0) {
        for (hour in 8..16) {
            for (lesson in filteredLessons)  {
                if (lesson.end.slice(0..1).toInt() == hour) {
                    println(lesson.end.slice(0..1).toInt().toString())
                    flag = true
                    paddingsTop.add((paddingTop))
                    paddingTop = 0
                    break
                }
            }
            if (flag) {
                flag = false
            }
            else {
                paddingTop = paddingTop + 65
            }
        }
        val pairArray = filteredLessons.zip(paddingsTop)
        LazyColumn {
            items(pairArray) {(lesson, padding) ->
                LessonColumnItem(lesson, padding)
            }
        }
    }
}