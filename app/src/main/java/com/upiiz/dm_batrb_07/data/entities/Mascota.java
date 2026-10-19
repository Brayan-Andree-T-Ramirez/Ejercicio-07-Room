package com.upiiz.dm_batrb_07.data.entities;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.processing.Generated;

@Entity(tableName =  "mascotas")
public class Mascota {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "raza")
    private String raza;
    @ColumnInfo(name="edad")
    private int edad;

    @ColumnInfo(name = "id_veterinario")
    private Long idVeterinario;


    public Mascota() {
    }

    public Mascota(Long id, String nombre, String raza, int edad, Long idVeterinario) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.idVeterinario = idVeterinario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Long getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Long idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
}
