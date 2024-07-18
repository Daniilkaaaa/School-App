package com.example.schoolapp.dateTime

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun CurrentDateTime(): String {
    val current = LocalDateTime.now()
    val year = current.year
    val month = current.monthValue
    val day = current.dayOfMonth
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
