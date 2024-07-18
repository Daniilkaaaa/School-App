package com.example.schoolapp.screens.today.TodayScreen.Components.topBar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.google.firebase.firestore.DocumentReference
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayRow(selectedDay: MutableState<Int>, db: DocumentReference) {
    val current = LocalDateTime.now()
    val startYear = current.year
    val startMonth = current.monthValue
    val startDay = current.dayOfMonth
    var currentDate = LocalDate.of(startYear, startMonth, startDay)
    val days = mutableListOf<Pair<String, String>>()
    for (i in 0..30) {
        days.add(Pair(currentDate.dayOfMonth.toString(), currentDate.dayOfWeek.getDisplayName(
            TextStyle.SHORT, Locale.getDefault())))
        currentDate = currentDate.plusDays(1)
    }
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        var i = 0
        items(days) { (number, day) ->
            DayRowItem(number, day, selectedDay, startDay, db)
        }
    }
}