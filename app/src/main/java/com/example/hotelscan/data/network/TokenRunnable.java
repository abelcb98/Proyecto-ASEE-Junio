package com.example.hotelscan.data.network;

import android.util.Log;

import com.example.hotelscan.data.modelo.Token;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenRunnable implements Runnable{

    private final OnTokenLoadedListener tslistener;
    private static final String grant_type="client_credentials";
    private static final String client_id="9VElYG9wUvcdFHTwRApSMMfqGpSfrqAZ";
    private static final String client_secret="GuoXRLulMBvaxrLF";


    public TokenRunnable(OnTokenLoadedListener tslistener){
        this.tslistener = tslistener;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test.api.amadeus.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TokenService service = retrofit.create(TokenService.class);
        try {
            Token k = service.getToken(grant_type,client_id,client_secret).execute().body();
            tslistener.onTokenLoaded(k);
            Log.d("FlyScann Abel","Chachio esto funciona o k: "+ k.getAccessToken());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

