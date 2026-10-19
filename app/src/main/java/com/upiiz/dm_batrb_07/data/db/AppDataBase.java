package com.upiiz.dm_batrb_07.data.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.upiiz.dm_batrb_07.data.dao.MascotaDao;
import com.upiiz.dm_batrb_07.data.dao.VeterinarioDao;
import com.upiiz.dm_batrb_07.data.entities.Mascota;
import com.upiiz.dm_batrb_07.data.entities.Veterinario;

@Database(entities = {Mascota.class, Veterinario.class} , version=1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MascotaDao mascotaDao();
    public abstract VeterinarioDao veterinarioDao();
}
