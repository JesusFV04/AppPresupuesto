package com.example.apppresupuesto.data.repository

import com.example.apppresupuesto.data.dao.IngresoDao
import com.example.apppresupuesto.data.entities.Ingreso

class IngresoRepository(private val ingresoDao: IngresoDao) {

    suspend fun insertar(ingreso: Ingreso): Long {
        return ingresoDao.insertar(ingreso)
    }

    suspend fun obtenerPorUsuario(usuarioId: Int): List<Ingreso> {
        return ingresoDao.obtenerPorUsuario(usuarioId)
    }

    suspend fun totalIngresos(usuarioId: Int): Double {
        return ingresoDao.totalIngresos(usuarioId) ?: 0.0
    }
}
