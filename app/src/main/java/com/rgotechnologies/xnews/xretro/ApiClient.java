package com.rgotechnologies.xnews.xretro;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL="https://test.rgotechnologies.com/wp/wp-json/wp/v2/";
    private static Retrofit retrofit=null;
    public static    Retrofit getApiClient(){
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
