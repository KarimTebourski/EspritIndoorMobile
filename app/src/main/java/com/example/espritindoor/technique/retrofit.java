package com.example.espritindoor.technique;

import com.example.espritindoor.URL.Url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofit {
    private static Retrofit retrofit;


    public static  Retrofit getInstance() {
        String url = Url.MonURL();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url+"/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit ;

    }
}
