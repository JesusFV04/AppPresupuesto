package com.example.apppresupuesto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.apppresupuesto.data.database.AppDatabase
import com.example.apppresupuesto.data.repository.UsuarioRepository
import com.example.apppresupuesto.ui.navigation.AppNavigation
import com.example.apppresupuesto.ui.theme.AppPresupuestoTheme
import com.example.apppresupuesto.viewmodel.SesionViewModel
import com.example.apppresupuesto.viewmodel.SesionViewModelFactory

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Instanciamos la base de datos y el repositorio
        val db = AppDatabase.getDatabase(this)
        val usuarioRepository = UsuarioRepository(db.usuarioDao())

        setContent {
            AppPresupuestoTheme {
                val navController = rememberNavController()

                // ViewModel con el repositorio
                val sesionViewModel: SesionViewModel = viewModel(
                    factory = SesionViewModelFactory(usuarioRepository)
                )

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(
                        navController = navController,
                        sesionViewModel = sesionViewModel
                    )
                }
            }
        }
    }
}
