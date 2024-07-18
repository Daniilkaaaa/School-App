package com.example.schoolapp.screens.settings

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
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.schoolapp.data.Subject
import com.example.schoolapp.ui.theme.BlueColor
import com.example.schoolapp.ui.theme.TextColorGrey
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassCreatingScreen(navController: NavController, idUser: String) {
    val controller = rememberColorPickerController()
    var name by remember { mutableStateOf("") }
    var shortName by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.White) }
    Box(modifier = Modifier
        //.background(BackgroundDarkest1)
        .fillMaxHeight()) {
        Column{
            TopBar(navController, name, shortName, color, idUser)
            Text(
                text = "New Class",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp)

            )
            Text(
                text = "Name",
                fontSize = 20.sp,
                color = TextColorGrey,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp)

            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp, end = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = TextColorGrey,
                    unfocusedBorderColor = TextColorGrey,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),

                value = name,
                onValueChange = { name = it },
            )
            Text(
                text = "Short name",
                fontSize = 20.sp,
                color = TextColorGrey,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp)

            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 10.dp, end = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = TextColorGrey,
                    unfocusedBorderColor = TextColorGrey,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),

                value = shortName,
                onValueChange = { shortName = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pick color:",
                fontSize = 25.sp,
                color = TextColorGrey,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp, end = 10.dp)

            )
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    color = colorEnvelope.color
                }
            )


        }
    }
}


@Composable
fun TopBar(navController: NavController, name: String, shortName: String, color: Color, idUser: String) {
    val db = Firebase.firestore.collection("users").document(idUser)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            //.background(BackgroundDarkest2),
        contentAlignment = Alignment.Center
    ){
        Row {
            Text(
                text = "Cansel",
                fontSize = 20.sp,
                color = BlueColor,
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp)
                    .weight(1f)
                    .clickable {

                    }
            )

            Text(
                text = "Save",
                fontSize = 20.sp,
                color = BlueColor,
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .clickable {
                        if (name != "") {
                            val colorString = Integer.toHexString(color.toArgb())
                            val subject = Subject(name, shortName = shortName, colorString)
                            db.collection("subjects").document().set(subject)
                            navController.navigate("settings")
                        }
                    }
            )
        }
    }
}
