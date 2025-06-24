package com.example.apppresupuesto.model

data class Gasto(
    val monto: Double,
    val categoria: String,
    val descripcion: String = ""
)
