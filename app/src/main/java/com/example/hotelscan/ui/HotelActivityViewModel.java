package com.example.hotelscan.ui;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelscan.data.HotelRepository;
import com.example.hotelscan.data.roomdb.HotelDB;

import java.util.List;

public class HotelActivityViewModel extends ViewModel {
    private final HotelRepository mRepository;
    private final LiveData<List<HotelDB>> mRepos;
    private final LiveData<List<HotelDB>> mRecentHotels;


    public HotelActivityViewModel(HotelRepository repository) {
        mRepository = repository;
        mRepos = mRepository.getCurrentHotel();
        mRecentHotels=mRepository.getAllCurrentHotel();
    }

    public void setHotel(HotelDB h, ProgressBar p){

        mRepository.setHotel(h, p);
    }


    public LiveData<List<HotelDB>> getHotels() {
        return mRepos;
    }

    public LiveData<List<HotelDB>> getmRecentHotels() {
        return mRecentHotels;
    }






}
