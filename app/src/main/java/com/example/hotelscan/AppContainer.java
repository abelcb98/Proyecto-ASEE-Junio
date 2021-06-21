package com.example.hotelscan;

import android.content.Context;

import com.example.hotelscan.data.HotelRepository;
import com.example.hotelscan.data.network.HotelNetworkDataSource;
import com.example.hotelscan.data.roomdb.HotelDatabase;
import com.example.hotelscan.ui.HotelViewModelFactory;

public class AppContainer {
    private HotelDatabase database;
    private HotelNetworkDataSource networkDataSource;
    public HotelRepository repository;
    public HotelViewModelFactory factory;


    public AppContainer(Context context) {
        database = HotelDatabase.getInstance(context);
        networkDataSource = HotelNetworkDataSource.getInstance();
        repository = HotelRepository.getInstance(database.getDao(), networkDataSource);
        factory = new HotelViewModelFactory(repository);
    }
}
