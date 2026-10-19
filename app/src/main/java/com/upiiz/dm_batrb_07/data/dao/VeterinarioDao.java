package com.upiiz.dm_batrb_07.data.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.upiiz.dm_batrb_07.data.entities.Veterinario;

import java.util.List;

@Dao
public interface VeterinarioDao {

    @Query("SELECT * FROM veterinarios")
    List<Veterinario> obtenerTodos();
    @Query("SELECT * FROM veterinarios where correo=:correo LIMIT 1")
    Veterinario obtenerPorCorreo(String correo);

    @Query("SELECT * FROM veterinarios where id=:id LIMIT 1")
    Veterinario obtenerPorId(Long id);
    @Insert
    void crear(Veterinario veterinario);
    @Update
    void actualizar(Veterinario veterinario);
    @Delete
    void eliminar(Veterinario veterinario);
}
