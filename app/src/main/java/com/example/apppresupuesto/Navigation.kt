package com.example.apppresupuesto.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apppresupuesto.ui.HomeScreen
import com.example.apppresupuesto.ui.ExpensesScreen

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("expenses") {
            ExpensesScreen(navController)
        }
    }
}
