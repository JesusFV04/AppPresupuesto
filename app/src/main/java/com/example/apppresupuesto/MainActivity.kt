package com.example.apppresupuesto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.apppresupuesto.ui.theme.AppPresupuestoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPresupuestoTheme {
                Navigation()
            }
        }
    }
}