package com.example.schoolapp.screens.assignments.assignmentsCreating

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.schoolapp.data.Subject
import com.example.schoolapp.ui.theme.TextColorGrey
import com.google.firebase.firestore.DocumentReference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(db: DocumentReference, onClassSelected: (String) -> Unit) {
    var subjects by remember { mutableStateOf(listOf<Subject>()) }
    db.collection("subjects").get().addOnSuccessListener { result ->
        subjects = result.map { document ->
            Subject(
                name = document.getString("name") ?: "",
                color = document.getString("color") ?: ""
            )
        }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }

    androidx.compose.material3.ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(top = 25.dp)
    ) {
        OutlinedTextField(
            value = subjects.getOrNull(selectedItemIndex)?.name ?: "",
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = TextColorGrey,
                unfocusedBorderColor = TextColorGrey,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .width(358.dp)
                .height(60.dp)
                .padding(start = 10.dp),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            subjects.forEachIndexed { index, subject ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = subject.name,
                            fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                        )
                    },
                    onClick = {
                        selectedItemIndex = index
                        onClassSelected(subject.name)
                        expanded = false
                    }
                )
            }
        }
    }
}