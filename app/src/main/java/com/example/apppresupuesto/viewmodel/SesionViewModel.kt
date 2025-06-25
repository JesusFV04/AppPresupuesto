package com.example.apppresupuesto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppresupuesto.data.entities.Usuario
import com.example.apppresupuesto.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SesionViewModel(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, contraseña: String) {
        viewModelScope.launch {
            val usuario = usuarioRepository.login(email, contraseña)
            if (usuario != null) {
                _usuarioActual.value = usuario
                _error.value = null
            } else {
                _error.value = "Credenciales inválidas"
            }
        }
    }

    fun registrar(email: String, contraseña: String) {
        viewModelScope.launch {
            val nuevoUsuario = Usuario(email = email, contraseña = contraseña)
            val id = usuarioRepository.insertar(nuevoUsuario)
            _usuarioActual.value = nuevoUsuario.copy(id = id.toInt())
            _error.value = null
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}
