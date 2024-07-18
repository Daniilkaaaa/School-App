package com.example.schoolapp.screens.assignments.assignmentsCreating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Subject
import com.example.schoolapp.ui.theme.BlueColor
import com.example.schoolapp.ui.theme.TextColorGrey
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssigmentCreatingScreen(navController: NavController, idUser: String) {
    val db = Firebase.firestore.collection("users").document(idUser)
    Box(modifier = Modifier
        //.background(BackgroundDarkest1)
        .fillMaxHeight()) {
        var title by remember { mutableStateOf("") }
        var className by remember { mutableStateOf("") }
        var details by remember { mutableStateOf("") }
        var priority by remember { mutableStateOf(false) }
        var allDays by remember { mutableStateOf(false) }
        var alert by remember { mutableStateOf(false) }
        Column {
            TopBar(title, className, details, priority, allDays, alert, db)
            Text(
                text = "New Assignment",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp)

            )
            Text(
                text = "Title",
                fontSize = 20.sp,
                color = TextColorGrey,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp)

            )
            OutlinedTextField(
                modifier = Modifier
                    .width(358.dp)
                    .height(60.dp)
                    .padding(start = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = TextColorGrey,
                    unfocusedBorderColor = TextColorGrey,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                value = title,
                onValueChange = { title = it },)
            ExposedDropdownMenuBox(db) { selectedClass ->
                className = selectedClass
            }
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                modifier = Modifier
                    .width(358.dp)
                    .height(60.dp)
                    .padding(start = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = TextColorGrey,
                    unfocusedBorderColor = TextColorGrey,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                value = details,
                onValueChange = { details = it },)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, top = 25.dp)
            ) {
                Checkbox(
                    checked = priority,
                    onCheckedChange = { priority = it }
                )
                Text(text = "Set as priority", color = Color.White, fontSize = 20.sp)
            }
            Divider(
                color = TextColorGrey,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(top = 25.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, top = 25.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "All day",
                    color = Color.White,
                    fontSize = 20.sp)
                Switch(
                    checked = allDays,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = BlueColor,
                        uncheckedTrackColor = TextColorGrey
                    ),
                    onCheckedChange = { allDays = it }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, top = 25.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Alert",
                    color = Color.White,
                    fontSize = 20.sp)
                Switch(
                    checked = alert,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = BlueColor,
                        uncheckedTrackColor = TextColorGrey
                    ),
                    onCheckedChange = { alert = it }
                )
            }
        }
    }
}
