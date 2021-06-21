package com.example.hotelscan.data.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {HotelDB.class, Ciudad.class}, version = 2)
public abstract class HotelDatabase extends RoomDatabase {
    private static HotelDatabase instance;

    public static HotelDatabase getInstance(Context context){
        if (instance==null)
            instance= Room.databaseBuilder(context, HotelDatabase.class,"hotelDB.db").build();
        return instance;
    }

    public abstract HotelDao getDao();
    public abstract CiudadDao getCiudadDao();

}
