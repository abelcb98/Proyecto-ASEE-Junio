package com.example.hotelscan.data.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Ciudades")
public class Ciudad
{

    @PrimaryKey
    @ColumnInfo(name = "cod_ciudad")
    @NonNull private String cod_ciudad;

    @ColumnInfo(name = "nombre")
    private String nombre;




    @ColumnInfo(name = "favorito")
    private String favorite;


    public Ciudad(String cod_ciudad, String nombre) {
        this.cod_ciudad = cod_ciudad;
        this.nombre = nombre;
        this.favorite = "0";
    }
    public Ciudad(String cod_ciudad, String nombre, String favorito) {
        this.cod_ciudad = cod_ciudad;
        this.nombre = nombre;
        this.favorite = favorito;
    }

    public Ciudad(Ciudad c){
        this.cod_ciudad = c.cod_ciudad;
        this.nombre = c.nombre;
        this.favorite = c.favorite;
    }

    // ####################
    // GETTERS AND SETTERS
    public String getCod_ciudad() {
        return cod_ciudad;
    }

    public void setCod_ciudad(String cod_ciudad) {
        this.cod_ciudad = cod_ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFavorite() {     return favorite;   }

    public void setFavorite(String favorite) { this.favorite = favorite;  }


}