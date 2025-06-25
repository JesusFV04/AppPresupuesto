package com.example.apppresupuesto.data.database

import android.content.Context
import androidx.room.*
import com.example.apppresupuesto.data.dao.*
import com.example.apppresupuesto.data.entities.*
import androidx.room.TypeConverters
import com.example.apppresupuesto.util.DateConverter

@Database(
    entities = [Usuario::class, Ingreso::class, Gasto::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun ingresoDao(): IngresoDao
    abstract fun gastoDao(): GastoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "presupuesto_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
