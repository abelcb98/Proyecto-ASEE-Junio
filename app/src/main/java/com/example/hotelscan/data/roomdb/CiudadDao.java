package com.example.hotelscan.data.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CiudadDao {

    @Query("SELECT * FROM ciudades WHERE favorito = 1 ")
    public List<Ciudad> getAllFav();

    @Query("SELECT * FROM ciudades WHERE favorito = 0 ")
    public List<Ciudad> getAllNoFav();


    @Query("SELECT * FROM ciudades")
    public List<Ciudad> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Ciudad c);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertList(List<Ciudad> ciudades);


    @Query("SELECT * FROM ciudades WHERE nombre LIKE :nombre||'%' ")
    public Ciudad findByName(String nombre);

    @Query("SELECT * FROM ciudades WHERE cod_ciudad LIKE :cod ")
    public List<Ciudad> findByCod(String cod);



    @Update
    public void update(Ciudad c);

    @Delete
    public void delete(Ciudad c);

    @Query("DELETE FROM ciudades")
    public void deleteAll();


}