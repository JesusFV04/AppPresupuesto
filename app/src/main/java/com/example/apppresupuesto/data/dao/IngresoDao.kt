package com.example.apppresupuesto.data.dao

import androidx.room.*
import com.example.apppresupuesto.data.entities.Ingreso

@Dao
interface IngresoDao {

    @Insert
    suspend fun insertar(ingreso: Ingreso): Long

    @Query("SELECT * FROM ingresos WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<Ingreso>

    @Query("SELECT SUM(monto) FROM ingresos WHERE usuarioId = :usuarioId")
    suspend fun totalIngresos(usuarioId: Int): Double?
}
