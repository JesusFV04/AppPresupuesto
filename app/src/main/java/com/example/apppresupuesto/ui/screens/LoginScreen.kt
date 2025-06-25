package com.example.apppresupuesto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.apppresupuesto.viewmodel.SesionViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    sesionViewModel: SesionViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var esRegistro by remember { mutableStateOf(false) }

    val error by sesionViewModel.error.collectAsState()

    // Navegar si hay usuario activo
    val usuario by sesionViewModel.usuarioActual.collectAsState()
    LaunchedEffect(usuario) {
        if (usuario != null) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (esRegistro) "Registrarse" else "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (esRegistro) {
                    sesionViewModel.registrar(email, contraseña)
                } else {
                    sesionViewModel.login(email, contraseña)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (esRegistro) "Registrarse" else "Iniciar sesión")
        }

        TextButton(
            onClick = { esRegistro = !esRegistro },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(if (esRegistro) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate")
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}
