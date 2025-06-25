package com.example.apppresupuesto.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "ingresos")
data class Ingreso(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Double,
    val fuente: String, // nuevo
    val nota: String = "", // nuevo
    val fecha: Date = Date(),
    val usuarioId: Int
)
