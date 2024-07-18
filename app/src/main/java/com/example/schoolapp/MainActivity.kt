package com.example.schoolapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.schoolapp.data.Lesson
import com.example.schoolapp.data.Subject
import com.example.schoolapp.screens.assignments.assignmentsCreating.AssigmentCreatingScreen
import com.example.schoolapp.screens.assignments.assignmentsList.AssignmentsScreen
import com.example.schoolapp.screens.settings.ClassCreatingScreen
import com.example.schoolapp.screens.settings.ClassEditingScreen
import com.example.schoolapp.screens.settings.LessonCreatingScreen.LessonCreatingScreen
import com.example.schoolapp.screens.shedule.ScheduleScreen.ScheduleScreen
import com.example.schoolapp.screens.settings.SettingsScreen.SettingsScreen
import com.example.schoolapp.screens.today.TodayScreen.TodayScreen
import com.example.schoolapp.screens.lessonEdit.LessonEditingScreen
import com.example.schoolapp.screens.login.SignIn
import com.example.schoolapp.screens.login.SignInScreen
import com.example.schoolapp.screens.login.SignUp
import com.example.schoolapp.screens.login.SignUpScreen
import com.example.schoolapp.ui.theme.BlueColor
import com.example.schoolapp.ui.theme.SchoolAppTheme
import com.google.gson.Gson


class MainActivity : ComponentActivity() {
    private val idUser = mutableStateOf("")
    fun toggleIdUser(id: String) {
        idUser.value = id
    }
    private val isDarkTheme = mutableStateOf(true)
    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolAppTheme(darkTheme = isDarkTheme.value) {
                val navController = rememberNavController()
                val showBottomBar = remember { mutableStateOf(true) }
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    showBottomBar.value = when (destination.route) {
                        "sign_up" -> false
                        "sign_in" -> false
                        else -> true
                    }
                }
                Scaffold(
                    bottomBar = {
                        if (showBottomBar.value) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = "sign_in", Modifier.padding(innerPadding)) {
                        composable("sign_up") { SignUpScreen(navController, this@MainActivity::toggleIdUser) }
                        composable("sign_in") { SignInScreen(navController, this@MainActivity::toggleIdUser) }
                        composable("today") { TodayScreen(navController, idUser.value) }
                        composable("schedule") { ScheduleScreen(idUser.value) }
                        composable("assignments") { AssignmentsScreen(navController, idUser.value) }
                        composable("settings") { SettingsScreen(navController, isDarkTheme, this@MainActivity::toggleTheme, idUser.value) }
                        composable("creating_class") { ClassCreatingScreen(navController, idUser.value) }
                        composable("editing_class/{subjectJson}", arguments = listOf(navArgument("subjectJson") { type = NavType.StringType }))
                        { backStackEntry ->
                            val userJson = backStackEntry.arguments?.getString("subjectJson")
                            val subject = Gson().fromJson(userJson, Subject::class.java)
                            ClassEditingScreen(navController, subject, idUser.value)
                        }
                        composable("creating_assigment") { AssigmentCreatingScreen(navController, idUser.value) }
                        composable("creating_lesson") { LessonCreatingScreen(navController, idUser.value) }
                        composable(
                            "editing_lesson/{lessonJson}",
                            arguments = listOf(navArgument("lessonJson") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val lessonJson = backStackEntry.arguments?.getString("lessonJson")
                            val lesson = Gson().fromJson(lessonJson, Lesson::class.java)
                            LessonEditingScreen(navController, lesson, idUser.value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    BottomNavigation(
        modifier = Modifier.height(70.dp),
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        @Composable
        fun bottomNavItem(
            route: String,
            iconId: Int,
            label: String
        ) {
            val isSelected = currentRoute == route
            val iconColor = if (isSelected) BlueColor else MaterialTheme.colorScheme.surface
            val textColor = if (isSelected) BlueColor else MaterialTheme.colorScheme.surface

            BottomNavigationItem(
                icon = {
                    val svgImage: Painter = painterResource(id = iconId)
                    Image(painter = svgImage, contentDescription = null, colorFilter = ColorFilter.tint(iconColor))
                },
                label = { Text(label, fontSize = 12.sp, color = textColor) },
                selected = isSelected,
                onClick = { navController.navigate(route) }
            )
        }
        bottomNavItem("today", R.drawable.today, "Today")
        bottomNavItem("schedule", R.drawable.schedulle, "Schedule")
        bottomNavItem("assignments", R.drawable.assignment, "Assignments")
        bottomNavItem("settings", R.drawable.settings, "Settings")
    }
}





