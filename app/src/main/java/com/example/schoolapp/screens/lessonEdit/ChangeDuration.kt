package com.example.schoolapp.screens.lessonEdit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.schoolapp.ui.theme.TextColorGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeDuration(onDurationSelected: (String) -> Unit) {
    val context = LocalContext.current
    val durations = arrayOf("08:00-08:40", "08:50-09:30", "09:40-10:20", "10:30-11:10", "11:40-12:20",
        "12:30-13:10", "13:20-14:00", "14:10-14:50", "15:40-16:10", "16:20-17:00")
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }

    androidx.compose.material3.ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
    ) {
        OutlinedTextField(
            value = durations[selectedItemIndex], // Установка выбранного значения
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
                .fillMaxWidth()
                .height(60.dp)
                .padding(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            durations.forEachIndexed { index, duration ->
                DropdownMenuItem(
                    text = { Text(text = duration) },
                    onClick = {
                        selectedItemIndex = index
                        onDurationSelected(duration) // Обновление выбранного значения
                        expanded = false
                    }
                )
            }
        }
    }
}