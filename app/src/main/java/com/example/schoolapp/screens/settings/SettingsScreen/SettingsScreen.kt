package com.example.schoolapp.screens.settings.SettingsScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.schoolapp.data.Subject
import com.example.schoolapp.screens.settings.SettingsScreen.Components.AddLessonButton
import com.example.schoolapp.screens.settings.SettingsScreen.Components.ClassesSettings
import com.example.schoolapp.screens.settings.SettingsScreen.Components.DarkModeSwitch
import com.example.schoolapp.screens.settings.SettingsScreen.Components.PersonalSettings
import com.example.schoolapp.screens.settings.SettingsScreen.Components.SettingsHeader
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun SettingsScreen(navController: NavController, isDarkTheme: MutableState<Boolean>, toggleTheme: () -> Unit, idUser: String) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxHeight()
    ) {
        var subjects by remember { mutableStateOf(listOf<Subject>()) }
        var userName by remember { mutableStateOf<String?>(null) }
        var email by remember { mutableStateOf<String?>(null) }
        val db = Firebase.firestore.collection("users").document(idUser)

        LaunchedEffect(key1 = idUser) {
            fetchUserData(db) { name, mail ->
                userName = name
                email = mail
            }
            fetchSubjects(db) { fetchedSubjects ->
                subjects = fetchedSubjects
            }
        }

        Column {
            SettingsHeader()
            PersonalSettings(navController, userName, email, db, idUser)
            ClassesSettings(navController, subjects)
            AddLessonButton(navController)
            DarkModeSwitch(isDarkTheme, toggleTheme)
        }
    }
}



fun fetchUserData(db: DocumentReference, onUserDataFetched: (String?, String?) -> Unit) {
    db.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val userName = documentSnapshot.getString("name")
            val email = documentSnapshot.getString("email")
            onUserDataFetched(userName, email)
        }
    }
}

fun fetchSubjects(db: DocumentReference, onSubjectsFetched: (List<Subject>) -> Unit) {
    db.collection("subjects").get().addOnSuccessListener { result ->
        val subjects = result.map { document ->
            Subject(
                name = document.getString("name") ?: "",
                shortName = document.getString("shortName") ?: "",
                color = document.getString("color") ?: ""
            )
        }
        onSubjectsFetched(subjects)
    }
}