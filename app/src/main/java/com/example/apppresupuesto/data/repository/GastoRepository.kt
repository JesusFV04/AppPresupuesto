package com.example.apppresupuesto.data.repository

import com.example.apppresupuesto.data.dao.GastoDao
import com.example.apppresupuesto.data.entities.Gasto

class GastoRepository(private val gastoDao: GastoDao) {

    suspend fun insertar(gasto: Gasto): Long {
        return gastoDao.insertar(gasto)
    }

    suspend fun obtenerPorUsuario(usuarioId: Int): List<Gasto> {
        return gastoDao.obtenerPorUsuario(usuarioId)
    }

    suspend fun totalGastos(usuarioId: Int): Double {
        return gastoDao.totalGastos(usuarioId) ?: 0.0
    }
}
