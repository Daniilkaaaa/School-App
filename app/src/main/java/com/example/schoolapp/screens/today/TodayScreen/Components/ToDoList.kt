package com.example.schoolapp.screens.today.TodayScreen.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.schoolapp.data.Assignments
import com.example.schoolapp.ui.theme.BlueColor
import com.google.firebase.firestore.DocumentReference

@Composable
fun TodoList(db: DocumentReference, items: List<Assignments>, onRemoveItem: (String) -> Unit, color: Color) {
    Column {
        items.forEach { item ->
            val acceptedState = remember { mutableStateOf(item.accepted) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = acceptedState.value,
                    colors = CheckboxDefaults.colors(
                        checkedColor = color,
                        checkmarkColor = MaterialTheme.colorScheme.tertiary,
                        uncheckedColor = MaterialTheme.colorScheme.tertiary
                    ),
                    onCheckedChange = { isChecked ->
                        acceptedState.value = isChecked
                        item.accepted = isChecked
                        updateAssigmentsAccepted(db, item, isChecked)
                    }
                )
                Text(
                    text = item.title,
                    color = MaterialTheme.colorScheme.tertiary,
                    textDecoration = if (acceptedState.value) TextDecoration.LineThrough else null,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = { deteleAssigment(db, item) }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        tint = BlueColor,
                        contentDescription = "Удалить")
                }
            }
        }
    }
}

fun updateAssigmentsAccepted(db: DocumentReference, item: Assignments, isChecked: Boolean) {
    db.collection("assignments")
        .whereEqualTo("title", item.title)
        .whereEqualTo("classname", item.classname)
        .whereEqualTo("details", item.details)
        .whereEqualTo("priority", item.priority)
        .whereEqualTo("all_day", item.all_day)
        .whereEqualTo("alert", item.alert)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                if (document["title"] == item.title) {
                    val documentId = document.id
                    db.collection("assignments").document(documentId)
                        .update("accepted", isChecked)

                }
            }
        }
}


fun deteleAssigment(db: DocumentReference, item: Assignments) {
    db.collection("assignments")
        .whereEqualTo("title", item.title)
        .whereEqualTo("classname", item.classname)
        .whereEqualTo("details", item.details)
        .whereEqualTo("priority", item.priority)
        .whereEqualTo("all_day", item.all_day)
        .whereEqualTo("alert", item.alert)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                if (document["title"] == item.title) {
                    val documentId = document.id
                    db.collection("assignments").document(documentId)
                        .delete()

                }
            }
        }
}