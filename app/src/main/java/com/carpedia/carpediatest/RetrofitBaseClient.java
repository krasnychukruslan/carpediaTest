package com.carpedia.carpediatest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBaseClient {
    public static Retrofit create() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl("http://private-3871d6-carpediatest.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
