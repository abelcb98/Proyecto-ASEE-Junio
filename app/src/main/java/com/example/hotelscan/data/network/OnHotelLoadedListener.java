package com.example.hotelscan.data.network;

import com.example.hotelscan.data.roomdb.HotelDB;

import java.util.List;

public interface OnHotelLoadedListener {
    public void onHotelLoaded(List<HotelDB> hoteles);
}
