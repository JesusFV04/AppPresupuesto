package com.example.apppresupuesto.data.dao

import androidx.room.*
import com.example.apppresupuesto.data.entities.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE email = :email AND contraseña = :contraseña LIMIT 1")
    suspend fun login(email: String, contraseña: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Usuario?
}
