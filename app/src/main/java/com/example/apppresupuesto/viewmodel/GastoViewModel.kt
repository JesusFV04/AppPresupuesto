package com.example.apppresupuesto.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.apppresupuesto.model.Gasto

class GastoViewModel : ViewModel() {

    var presupuestoTotal by mutableStateOf(0.0)
        private set

    var presupuestoDefinido by mutableStateOf(false)
        private set

    private val _gastos = mutableStateListOf<Gasto>()
    val gastos: List<Gasto> get() = _gastos

    val gastoAcumulado: Double
        get() = _gastos.sumOf { it.monto }

    val presupuestoRestante: Double
        get() = presupuestoTotal - gastoAcumulado

    fun definirPresupuesto(valor: Double) {
        presupuestoTotal = valor
        _gastos.clear()
        presupuestoDefinido = true
    }

    fun agregarGasto(gasto: Gasto) {
        if (gasto.monto > 0 && gastoAcumulado + gasto.monto <= presupuestoTotal) {
            _gastos.add(gasto)
        }
    }
}
