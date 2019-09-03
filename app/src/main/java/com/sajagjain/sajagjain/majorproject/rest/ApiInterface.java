package com.sajagjain.sajagjain.majorproject.rest;

/**
 * Created by sajag jain on 02-10-2017.
 */

import com.sajagjain.sajagjain.majorproject.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("forecast")
    Call<WeatherResponse> getWeatherReport(@Query("appid") String appid, @Query("lat") String lat, @Query("lon") String lng
            , @Query("cnt") int cnt, @Query("type") String type);

//    @GET("movie/{id}")
//    Call<WeatherResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
