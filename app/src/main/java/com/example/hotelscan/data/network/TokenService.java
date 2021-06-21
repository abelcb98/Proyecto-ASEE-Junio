package com.example.hotelscan.data.network;

import com.example.hotelscan.data.modelo.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenService {
    @FormUrlEncoded
    @POST("security/oauth2/token")
    Call<Token> getToken(@Field("grant_type") String type, @Field("client_id") String client_id, @Field("client_secret") String client_secret);
}
