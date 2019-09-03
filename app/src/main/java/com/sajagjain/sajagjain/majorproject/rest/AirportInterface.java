package com.sajagjain.sajagjain.majorproject.rest;

import com.sajagjain.sajagjain.majorproject.model.AirportNearByResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sajag jain on 29-12-2017.
 */

public interface AirportInterface {
    //    nearby?api_key=4888f2d1-0506-422c-9a4f-7f87bae8eefa&lat=-6.1744&lng=106.8294&distance=1000
    @GET("nearby")
    Call<AirportNearByResponse> getNearByAirports(@Query("api_key") String apikey, @Query("lat") String lat
            , @Query("lng") String lng, @Query("distance") int cnt);
}
