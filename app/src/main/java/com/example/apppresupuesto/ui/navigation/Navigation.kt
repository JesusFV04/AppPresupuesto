package com.example.apppresupuesto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apppresupuesto.ui.screens.*
import com.example.apppresupuesto.viewmodel.SesionViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    sesionViewModel: SesionViewModel
) {
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                sesionViewModel = sesionViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onIrAGastos = {
                    navController.navigate("gastos")
                },
                onIrAIngresos = {
                    navController.navigate("ingresos")
                },
                onCerrarSesion = {
                    sesionViewModel.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("gastos") {
            ExpensesScreen(
                usuario = sesionViewModel.usuarioActual.value ?: return@composable,
                onVolverInicio = {
                    navController.navigate("home") {
                        popUpTo("gastos") { inclusive = true }
                    }
                }
            )
        }

        composable("ingresos") {
            IncomeScreen(
                usuario = sesionViewModel.usuarioActual.value ?: return@composable,
                onVolverInicio = {
                    navController.navigate("home") {
                        popUpTo("ingresos") { inclusive = true }
                    }
                }
            )
        }
    }
}
