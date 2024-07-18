package com.example.schoolapp.screens.login

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun SignUpScreen(navController: NavController, toggleIdUser: KFunction1<String, Unit>) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "User Name", fontSize = 25.sp, color = MaterialTheme.colorScheme.tertiary)
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

            value = nameState.value,
            onValueChange = { nameState.value = it },
        )
        Spacer(modifier = Modifier.height(10.dp))
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
            if ((nameState.value != "") && (emailState.value != "") && (passwordState.value != "")) {
                SignUp(navController, auth, nameState.value, emailState.value, passwordState.value, db)
            }
        }) {
            Text(text = "Sign Up", color = MaterialTheme.colorScheme.tertiary)
        }

    }
}

fun SignUp(navController: NavController, auth: FirebaseAuth, name: String, email: String, password: String, db: FirebaseFirestore) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                userId?.let { uid ->
                    navController.navigate("sign_in")
                    db.collection("users").get().addOnCompleteListener { collectionTask ->
                        if (collectionTask.isSuccessful) {
                            val userDocument = db.collection("users").document(uid)
                            val newUser = hashMapOf(
                                "email" to email,
                                "name" to name
                            )
                            userDocument.set(newUser).addOnSuccessListener {
                                Log.d("MyLog", "New user document created")
                            }.addOnFailureListener { e ->
                                Log.w("MyLog", "Error adding document", e)
                            }
                        }
                    }
                }
                Log.d("MyLog", "Sign Up successful")
            } else {
                task.exception?.message?.let {
                    Log.w("MyLog", "Sign Up failed: $it")
                }
            }
        }
}