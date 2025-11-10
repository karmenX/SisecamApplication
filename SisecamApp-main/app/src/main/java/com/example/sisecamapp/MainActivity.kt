package com.sisecam.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sisecam.app.ui.theme.SisecamAppTheme
import com.sisecam.app.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SisecamAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SisecamApp()
                }
            }
        }
    }
}

@Composable
fun SisecamApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToNotifications = { navController.navigate("notifications") },
                onNavigateToAttendance = { navController.navigate("attendance") },
                onNavigateToRoute = { navController.navigate("route") }
            )
        }

        composable("attendance") {
            AttendanceScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("notifications") {
            NotificationScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("route") {
            RouteScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}