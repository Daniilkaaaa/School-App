package com.example.schoolapp.screens.settings.SettingsScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.R
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase

@Composable
fun PersonalSettings(navController: NavController, userName: String?, email: String?, db: DocumentReference, idUser: String) {
    var showDialogDeleteAccount by remember { mutableStateOf(false) }
    var showDialogSignOut by remember { mutableStateOf(false) }
    if (showDialogDeleteAccount) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.primary,
            onDismissRequest = { showDialogDeleteAccount = false },
            title = { Text("Delete account", color = MaterialTheme.colorScheme.tertiary) },
            text = {
                Text(
                    "Are you sure you want to delete your account?",
                        color = MaterialTheme.colorScheme.tertiary
                )
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(containerColor = BlueColor),
                    onClick = {
                    db.delete()
                    val auth = Firebase.auth
                    auth.currentUser?.delete()
                    showDialogDeleteAccount = false
                    navController.navigate("sign_in")
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.tertiary)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(containerColor = BlueColor),
                    onClick = { showDialogDeleteAccount = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        )
    }
    if (showDialogSignOut) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.primary,
            onDismissRequest = { showDialogSignOut = false },
            title = { Text("Delete account", color = MaterialTheme.colorScheme.tertiary) },
            text = {
                Text(
                    "Are you sure you want to sign out in your account?",
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                    val auth = Firebase.auth
                    auth.signOut()
                    navController.navigate("sign_in")
                },  colors = ButtonDefaults.buttonColors(containerColor = BlueColor)) {
                    Text("Sign Out", color = MaterialTheme.colorScheme.tertiary)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(containerColor = BlueColor),
                    onClick = { showDialogSignOut = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        )
    }
    Text(
        text = "Personal settings",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .padding(start = 10.dp)
    )
    Spacer(Modifier.height(25.dp))
    Row {
        val svgImage: Painter = painterResource(id = R.drawable.userlogo)
        Image(
            painter = svgImage,
            contentDescription = null,
            modifier = Modifier.padding(start = 10.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            text = userName ?: "",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
    Spacer(Modifier.height(20.dp))
    TabRowDefaults.Divider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
    Spacer(Modifier.height(20.dp))
    Row {
        val svgImage: Painter = painterResource(id = R.drawable.maillogo)
        Image(
            painter = svgImage,
            contentDescription = null,
            modifier = Modifier.padding(start = 10.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            text = email ?: "",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
    Spacer(Modifier.height(10.dp))
    Text("Delete account",
        color = MaterialTheme.colorScheme.tertiary,
        fontSize = 20.sp,
        modifier = Modifier.clickable { showDialogDeleteAccount = true
        }
    )
    Spacer(Modifier.height(10.dp))
    Text("Sign Out",
        color = MaterialTheme.colorScheme.tertiary,
        fontSize = 20.sp,
        modifier = Modifier.clickable { showDialogSignOut = true
        })
}

