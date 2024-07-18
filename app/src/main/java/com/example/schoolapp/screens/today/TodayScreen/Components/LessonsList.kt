import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.screens.today.TodayScreen.Components.LessonContainer
import com.google.firebase.firestore.DocumentReference

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LessonsList(navController: NavController, selectedDay: MutableState<Int>, db: DocumentReference) {
    val lessons = remember { mutableStateListOf<Lesson>() }

    LaunchedEffect(Unit) {
        db.collection("classes").addSnapshotListener { snapshots, e ->
            if (e != null) return@addSnapshotListener
            lessons.clear()
            snapshots?.forEach { document ->
                val lesson = document.toObject(Lesson::class.java)
                lessons.add(lesson)
            }
        }
    }

    val filteredLessons = lessons
        .filter { it.day == selectedDay.value }
        .sortedBy { it.begining }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 174.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentSize(align = Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredLessons) { lesson ->
                LessonContainer(navController, lesson, db)
            }
        }
    }
}


