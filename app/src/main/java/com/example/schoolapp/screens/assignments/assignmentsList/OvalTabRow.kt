package com.example.schoolapp.screens.assignments.assignmentsList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.schoolapp.ui.theme.BlueColor

@Composable
fun OvalTabRow(items: List<String>, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(MaterialTheme.colorScheme.primary)
            .height(45.dp)
            .width(358.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(if (index == selectedTabIndex) BlueColor else MaterialTheme.colorScheme.primary)
                        .clickable { onTabSelected(index) }
                        .padding(14.dp)
                ) {
                    Text(
                        text = text,
                        fontWeight = FontWeight.Bold,
                        color = if (index == selectedTabIndex) Color.Black else MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentHeight(Alignment.CenterVertically)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}