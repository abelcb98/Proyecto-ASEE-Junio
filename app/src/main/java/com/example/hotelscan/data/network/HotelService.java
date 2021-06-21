package com.example.hotelscan.data.network;


import com.example.hotelscan.data.modelo.Hotel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HotelService {
   @GET("shopping/hotel-offers")
   Call<Hotel> listHoteles(@Header("Authorization") String Authorization, @Query("cityCode") String cityCode, @Query("checkInDate") String checkInDate, @Query("checkOutDate") String checkOutDate, @Query("adults") String adults);
}
