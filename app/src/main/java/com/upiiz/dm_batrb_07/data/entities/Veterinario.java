package com.upiiz.dm_batrb_07.data.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "veterinarios")
public class Veterinario {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "correo")
    private String correo;

    @ColumnInfo(name =  "contrasenia")
    private String contrasenia;

    public Veterinario() {

    }

    public Veterinario(Long id, String nombre, String correo, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
