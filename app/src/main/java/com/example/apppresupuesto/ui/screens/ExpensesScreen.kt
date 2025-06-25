package com.example.apppresupuesto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.apppresupuesto.data.database.AppDatabase
import com.example.apppresupuesto.data.entities.Gasto
import com.example.apppresupuesto.data.entities.Usuario
import androidx.compose.ui.platform.LocalContext
import com.example.apppresupuesto.data.repository.GastoRepository
import com.example.apppresupuesto.data.repository.IngresoRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExpensesScreen(
    usuario: Usuario,
    onVolverInicio: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    val gastoRepository = remember { GastoRepository(db.gastoDao()) }
    val ingresoRepository = remember { IngresoRepository(db.ingresoDao()) }

    val scope = rememberCoroutineScope()

    var categoria by remember { mutableStateOf("") }
    var montoInput by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var gastos by remember { mutableStateOf(listOf<Gasto>()) }
    var totalIngresos by remember { mutableDoubleStateOf(0.0) }
    var totalGastos by remember { mutableDoubleStateOf(0.0) }
    var error by remember { mutableStateOf<String?>(null) }

    // Cargar gastos e ingresos al iniciar
    LaunchedEffect(Unit) {
        gastos = gastoRepository.obtenerPorUsuario(usuario.id)
        totalGastos = gastoRepository.totalGastos(usuario.id)
        totalIngresos = ingresoRepository.totalIngresos(usuario.id)
    }

    val presupuestoRestante = totalIngresos - totalGastos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Presupuesto actual: S/ %.2f".format(totalIngresos), style = MaterialTheme.typography.titleMedium)
        Text("Gasto acumulado: S/ %.2f".format(totalGastos))
        Text("Restante: S/ %.2f".format(presupuestoRestante), color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = montoInput,
            onValueChange = { montoInput = it },
            label = { Text("Monto del gasto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            val monto = montoInput.toDoubleOrNull()
            if (monto == null || monto <= 0.0) {
                error = "Monto inválido"
                return@Button
            }
            if (monto > presupuestoRestante) {
                error = "Este gasto excede tu presupuesto restante"
                return@Button
            }

            val nuevoGasto = Gasto(
                monto = monto,
                categoria = categoria,
                descripcion = descripcion,
                usuarioId = usuario.id
            )

            scope.launch {
                gastoRepository.insertar(nuevoGasto)
                gastos = gastoRepository.obtenerPorUsuario(usuario.id)
                totalGastos = gastoRepository.totalGastos(usuario.id)
                error = null
                montoInput = ""
                categoria = ""
                descripcion = ""
            }
        }) {
            Text("Agregar gasto")
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        Text("Historial de gastos:", style = MaterialTheme.typography.titleMedium)

        LazyColumn(modifier = Modifier.fillMaxHeight(0.6f)) {
            items(gastos) { gasto ->
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val fecha = sdf.format(gasto.fecha)

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("S/ %.2f - %s".format(gasto.monto, gasto.categoria))
                    if (gasto.descripcion.isNotBlank()) {
                        Text(gasto.descripcion, style = MaterialTheme.typography.bodySmall)
                    }
                    Text("Fecha: $fecha", style = MaterialTheme.typography.bodySmall)
                }
                HorizontalDivider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onVolverInicio) {
            Text("Volver al inicio")
        }
    }
}
