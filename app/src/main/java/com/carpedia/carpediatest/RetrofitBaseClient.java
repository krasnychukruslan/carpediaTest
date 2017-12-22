package com.carpedia.carpediatest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rusci on 22-Dec-17.
 */

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
