package com.example.apppresupuesto.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.apppresupuesto.data.database.AppDatabase
import com.example.apppresupuesto.data.entities.Ingreso
import com.example.apppresupuesto.data.entities.Usuario
import com.example.apppresupuesto.data.repository.IngresoRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun IncomeScreen(
    usuario: Usuario,
    onVolverInicio: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val ingresoRepository = remember { IngresoRepository(db.ingresoDao()) }
    val scope = rememberCoroutineScope()

    var montoInput by remember { mutableStateOf("") }
    var fuente by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf(Date()) }
    var ingresos by remember { mutableStateOf(listOf<Ingreso>()) }
    var error by remember { mutableStateOf<String?>(null) }

    // Al iniciar, cargar ingresos
    LaunchedEffect(Unit) {
        ingresos = ingresoRepository.obtenerPorUsuario(usuario.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Registrar Ingreso", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = montoInput,
            onValueChange = { montoInput = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fuente,
            onValueChange = { fuente = it },
            label = { Text("Fuente o descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            // Mostrar DatePicker y TimePicker
            val calendar = Calendar.getInstance()
            calendar.time = fecha

            DatePickerDialog(context,
                { _, year, month, day ->
                    TimePickerDialog(context,
                        { _, hour, minute ->
                            calendar.set(year, month, day, hour, minute)
                            fecha = calendar.time
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text("Seleccionar fecha/hora")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val monto = montoInput.toDoubleOrNull()
            if (monto == null || monto <= 0.0) {
                error = "Monto inválido"
                return@Button
            }

            val nuevoIngreso = Ingreso(
                monto = monto,
                fuente = fuente,
                fecha = fecha,
                usuarioId = usuario.id
            )

            scope.launch {
                ingresoRepository.insertar(nuevoIngreso)
                ingresos = ingresoRepository.obtenerPorUsuario(usuario.id)
                error = null
                montoInput = ""
                fuente = ""
            }
        }) {
            Text("Agregar ingreso")
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        Text("Historial de ingresos", style = MaterialTheme.typography.titleMedium)

        LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
            items(ingresos) { ingreso ->
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val fechaTexto = sdf.format(ingreso.fecha)

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("S/ %.2f - %s".format(ingreso.monto, ingreso.fuente))
                    Text("Fecha: $fechaTexto", style = MaterialTheme.typography.bodySmall)
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
