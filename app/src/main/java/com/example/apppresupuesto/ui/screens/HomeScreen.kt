package com.example.apppresupuesto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onIrAGastos: () -> Unit,
    onIrAIngresos: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a AppPresupuesto",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Gestiona tus ingresos y gastos de forma clara y organizada.",
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onIrAGastos, modifier = Modifier.fillMaxWidth()) {
            Text("Ir a mis gastos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onIrAIngresos, modifier = Modifier.fillMaxWidth()) {
            Text("Ir a mis ingresos")
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = onCerrarSesion) {
            Text("Cerrar sesión")
        }
    }
}
