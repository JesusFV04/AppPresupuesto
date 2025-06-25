package com.example.apppresupuesto.data.dao

import androidx.room.*
import com.example.apppresupuesto.data.entities.Gasto

@Dao
interface GastoDao {

    @Insert
    suspend fun insertar(gasto: Gasto): Long

    @Query("SELECT * FROM gastos WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<Gasto>

    @Query("SELECT SUM(monto) FROM gastos WHERE usuarioId = :usuarioId")
    suspend fun totalGastos(usuarioId: Int): Double?
}
