package com.upiiz.dm_batrb_07.data.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.upiiz.dm_batrb_07.data.entities.Mascota;

import java.util.List;

@Dao
public interface MascotaDao {
    //Query = consulta
    @Query("SELECT * FROM mascotas")
    List<Mascota> obtenerTodas();
    @Query("SELECT * FROM mascotas WHERE id=:id")
    Mascota obtenerPorId(Long id);

    @Query("SELECT * FROM mascotas WHERE id_veterinario=:idVeterinario")
    List<Mascota> obtenerMascotasVeterinario(Long idVeterinario);
    //Si se requiere el ID generado - Long
    //De lo contrario void
    @Insert
    void crear(Mascota mascota);
    @Update
    void actualizar(Mascota mascota);
    @Delete
    void eliminar(Mascota mascota);
}
