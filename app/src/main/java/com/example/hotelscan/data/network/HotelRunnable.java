package com.example.hotelscan.data.network;

import android.util.Log;
import android.widget.ProgressBar;


import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.data.roomdb.HotelDB;
import com.example.hotelscan.data.modelo.Datum;
import com.example.hotelscan.data.modelo.Hotel;
import com.example.hotelscan.data.modelo.Offer;
import com.example.hotelscan.data.modelo.Price;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelRunnable implements Runnable {

    private OnHotelLoadedListener listener;
    private String authorization;
    private String cityCode;
    private String fechaIni;
    private String fechaFin;
    private String numPersonas;
    private ProgressBar p;

    public HotelRunnable(String authorization, String cityCode, String fechaIni, String fechaFin, String numPersonas, ProgressBar p, OnHotelLoadedListener listener) {
        this.listener = listener;
        this.authorization = authorization;
        this.cityCode = cityCode;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.numPersonas = numPersonas;
        this.p=p;
    }



    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://test.api.amadeus.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
     HotelService service = retrofit.create(HotelService.class);
        try {
            Hotel hoteles= service.listHoteles(authorization,cityCode,fechaIni,fechaFin,numPersonas).execute().body();
            List<HotelDB> hotel_list = new ArrayList<HotelDB>();
            if (hoteles != null ){
            for (Datum datum : hoteles.getData()) {
                if (datum.getOffers().size() > 0) {
                    Price min = datum.getOffers().get(0).getPrice();
                    for (Offer f : datum.getOffers()) {
                        if (Double.parseDouble(f.getPrice().getTotal()) < Double.parseDouble(min.getTotal())) {
                            min = f.getPrice();
                        }
                    }
                    String direccion = "";
                    if (datum.getHotel().getAddress().getLines().size() > 0) {
                        direccion = datum.getHotel().getAddress().getLines().get(0);
                    }
                    String imagenes = null;
                    if (datum.getHotel().getMedia().size()>0)
                        imagenes = datum.getHotel().getMedia().get(0).getUri();
                    hotel_list.add(new HotelDB(datum.getHotel().getName(), datum.getHotel().getRating(), min.getTotal(), direccion, datum.getHotel().getContact().getPhone(), 0, fechaIni, fechaFin, numPersonas, imagenes,cityCode));
                }
            }}
            Log.i("FlyScan Abel: ",service.listHoteles(authorization,cityCode,fechaIni,fechaFin,numPersonas).execute().raw().toString());
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {

                        listener.onHotelLoaded(hotel_list);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
