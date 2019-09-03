package com.sajagjain.sajagjain.majorproject.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sajag jain on 13-12-2017.
 */

public class PlaceDescriptionApiClient {
    //https://en.wikipedia.org/w/api.php?action=parse&page=Delhi&prop=text&format=json&mobileformat=true

    public static final String BASE_URL = "https://en.wikipedia.org/w/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
