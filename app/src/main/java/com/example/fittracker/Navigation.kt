package com.example.fittracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, onLoginSuccess = { navController.navigate("main") })
        }
        composable("register") {
            RegisterScreen(navController, onRegisterSuccess = { navController.navigate("main") })
        }
        composable("main") {
            MyApp(navController)
        }
    }
}
