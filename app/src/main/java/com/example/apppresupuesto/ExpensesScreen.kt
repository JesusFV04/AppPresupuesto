package com.example.apppresupuesto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.apppresupuesto.model.Gasto
import com.example.apppresupuesto.viewmodel.GastoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(navController: NavController, viewModel: GastoViewModel = viewModel()) {
    var montoInput by remember { mutableStateOf("") }
    var categoriaInput by remember { mutableStateOf("") }
    var descripcionInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Presupuesto") },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!viewModel.presupuestoDefinido) {
                item {
                    Text("Define tu presupuesto inicial", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = montoInput,
                        onValueChange = { montoInput = it },
                        label = { Text("Presupuesto total (S/)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val valor = montoInput.toDoubleOrNull()
                            if (valor != null && valor > 0) {
                                viewModel.definirPresupuesto(valor)
                                montoInput = ""
                                errorMessage = null
                            } else {
                                errorMessage = "Ingresa un presupuesto válido"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Definir Presupuesto")
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            } else {
                // Card con resumen
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Presupuesto total: S/ ${viewModel.presupuestoTotal}", fontWeight = FontWeight.Bold)
                            Text("Gastado: S/ ${viewModel.gastoAcumulado}")
                            Text("Disponible: S/ ${viewModel.presupuestoRestante}")
                        }
                    }
                }

                // Formulario
                item {
                    Text("Registrar nuevo gasto", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = montoInput,
                        onValueChange = { montoInput = it },
                        label = { Text("Monto (S/)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = categoriaInput,
                        onValueChange = { categoriaInput = it },
                        label = { Text("Categoría") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = descripcionInput,
                        onValueChange = { descripcionInput = it },
                        label = { Text("Descripción (opcional)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    errorMessage?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    Button(
                        onClick = {
                            val monto = montoInput.toDoubleOrNull()
                            if (monto == null || monto <= 0) {
                                errorMessage = "Monto inválido"
                                return@Button
                            }
                            if (categoriaInput.isBlank()) {
                                errorMessage = "Categoría requerida"
                                return@Button
                            }
                            if (monto > viewModel.presupuestoRestante) {
                                errorMessage = "El gasto excede el presupuesto disponible"
                                return@Button
                            }

                            viewModel.agregarGasto(
                                Gasto(
                                    monto = monto,
                                    categoria = categoriaInput,
                                    descripcion = descripcionInput
                                )
                            )
                            montoInput = ""
                            categoriaInput = ""
                            descripcionInput = ""
                            errorMessage = null
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar Gasto")
                    }
                }

                // Historial de gastos
                item {
                    Text("Historial de gastos", style = MaterialTheme.typography.titleMedium)
                }

                items(viewModel.gastos) { gasto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Monto: S/ ${gasto.monto}", fontWeight = FontWeight.Medium)
                            Text("Categoría: ${gasto.categoria}")
                            if (gasto.descripcion.isNotBlank()) {
                                Text("Descripción: ${gasto.descripcion}")
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Volver al inicio")
                    }
                }
            }
        }
    }
}

