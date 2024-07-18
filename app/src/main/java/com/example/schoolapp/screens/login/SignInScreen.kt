package com.example.schoolapp.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.ui.theme.TextColorGrey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController, toggleIdUser: KFunction1<String, Unit>) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val emailState = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val passwordState = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Email", fontSize = 25.sp, color = MaterialTheme.colorScheme.tertiary)
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 30.dp, end = 30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),

            value = emailState.value,
            onValueChange = { emailState.value = it },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Password", fontSize = 25.sp, color = MaterialTheme.colorScheme.tertiary)
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 30.dp, end = 30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if ((emailState.value != "") && (passwordState.value != "")) {
                SignIn(auth, db, emailState.value, passwordState.value, toggleIdUser, navController, snackbarHostState)
            }
        }) {
            Text(text = "Sign In", color = MaterialTheme.colorScheme.tertiary)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Are you not registered?", color = Color.White, modifier =  Modifier.clickable { navController.navigate("sign_up") })
        SnackbarHost(hostState = snackbarHostState)
    }
}
fun SignIn(auth: FirebaseAuth, db: FirebaseFirestore, email: String, password: String, toggleIdUser: KFunction1<String, Unit>, navController: NavController, snackbarHostState: SnackbarHostState) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                userId?.let { uid ->
                    val userDocument = db.collection("users").document(uid)
                    toggleIdUser(uid)
                    navController.navigate("today")
                    userDocument.get().addOnSuccessListener { document ->
                        if (document.exists()) {
                            Log.d("MyLog", "DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d("MyLog", "No such document")
                        }
                    }.addOnFailureListener { e ->

                        Log.w("MyLog", "Error getting document", e)
                    }
                }
                Log.d("MyLog", "Sign In successful")
            } else {
                task.exception?.message?.let {
                    Log.w("MyLog", "Sign In failed: $it")
                }
            }
        }
}
