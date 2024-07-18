package com.example.schoolapp.data

data class Assignments(
    val title: String = "",
    val classname: String = "",
    val details: String = "",
    val priority: Boolean = false,
    val all_day: Boolean = false,
    val alert: Boolean = false,
    var accepted: Boolean = false,
)
