package com.example.hotelscan.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelscan.data.HotelRepository;

public class HotelViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final HotelRepository mRepository;

    public HotelViewModelFactory(HotelRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new HotelActivityViewModel(mRepository);
    }




}
