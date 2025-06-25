package com.example.apppresupuesto.data.repository

import com.example.apppresupuesto.data.dao.UsuarioDao
import com.example.apppresupuesto.data.entities.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun insertar(usuario: Usuario): Long {
        return usuarioDao.insertar(usuario)
    }

    suspend fun login(email: String, contraseña: String): Usuario? {
        return usuarioDao.login(email, contraseña)
    }

    suspend fun obtenerPorId(id: Int): Usuario? {
        return usuarioDao.obtenerPorId(id)
    }
}
