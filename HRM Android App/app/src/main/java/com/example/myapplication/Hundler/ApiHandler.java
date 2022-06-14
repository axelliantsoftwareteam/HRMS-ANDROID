package com.example.myapplication.Hundler;

import com.example.myapplication.Interface.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler
{
   // public static final String PRODUCTION_URL = "https://devhris.khazanapk.com/api/";
    public static final String LIVE_URL = "https://hris.khazanapk.com/api/";
    private static ApiInterface apiInterface;

    public static ApiInterface getApiInterface() {

        if (apiInterface == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(LIVE_URL)
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
            return apiInterface;
        } else {
            return apiInterface;
        }
    }

}
