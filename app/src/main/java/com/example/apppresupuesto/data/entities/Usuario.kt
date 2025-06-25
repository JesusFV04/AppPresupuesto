package com.example.apppresupuesto.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val contraseña: String // Puedes luego encriptar esto
)
