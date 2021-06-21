package com.example.hotelscan.data.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "hotel")
public class HotelDB {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long ID;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "puntuacion")
    private String puntuacion;
    @ColumnInfo(name = "precio")
    private String precio;
    @ColumnInfo(name = "calle")
    private String calle;
    @ColumnInfo(name = "contacto")
    private String contacto;
    @ColumnInfo(name="favorito")
    private int favorito;
    @ColumnInfo(name = "fechaini")
    private String fecha_inicio;
    @ColumnInfo(name = "fechafin")
    private String fecha_fin;
    @ColumnInfo(name = "numpersonas")
    private String num_personas;
    @ColumnInfo(name="imagen")
    private String imagen;
    @ColumnInfo(name="ciudad")
    private String ciudad;

    @Ignore
    public HotelDB(String nombre, String rate, String precio, String calle, String contacto, int favorito, String fecha_inicio, String fecha_fin, String num_personas, String imagen, String ciudad) {
        //this.ID = ID;
        this.nombre = nombre;
        this.puntuacion = rate;
        this.precio = precio;
        this.calle = calle;
        this.contacto = contacto;
        this.favorito = favorito;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.num_personas = num_personas;
        this.imagen = imagen;
        this.ciudad=ciudad;
    }

    public HotelDB() {
    }

    @NonNull
    public long getID() {
        return ID;
    }

    public void setID(@NonNull long ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }


    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getNum_personas() {
        return num_personas;
    }

    public void setNum_personas(String num_personas) {
        this.num_personas = num_personas;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
