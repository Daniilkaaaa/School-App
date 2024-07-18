package com.example.schoolapp.data

import java.time.Month

data class Lesson(
    val name: String = "",
    val shortName: String = "",
    val color: String = "",
    val room: Int = 0,
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
    val begining: String = "",
    val end: String = "",
)
