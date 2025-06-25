package com.example.apppresupuesto.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Double,
    val categoria: String,
    val descripcion: String = "",
    val fecha: Date = Date(),
    val usuarioId: Int
)
