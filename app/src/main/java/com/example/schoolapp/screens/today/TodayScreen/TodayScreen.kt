package com.example.schoolapp.screens.today.TodayScreen

import LessonsList
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.schoolapp.screens.today.TodayScreen.Components.topBar.TopBar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayScreen(navController: NavController, idUser: String) {
    val db = Firebase.firestore.collection("users").document(idUser)
    var selectedDay = remember { mutableStateOf(LocalDate.now().dayOfMonth) }
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.secondary)) {
        TopBar(selectedDay, db)
        LessonsList(navController, selectedDay, db)
    }
}











