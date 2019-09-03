package com.sajagjain.sajagjain.majorproject.rest;

import com.sajagjain.sajagjain.majorproject.model.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by sajag jain on 18-12-2017.
 */

public interface ZomatoApiInterface {

    @GET("collections")
    Call<RestaurantResponse> getRestaurantCollection(@Header("user-key") String userKey, @Query("lat") double lat
            , @Query("lon") double lon, @Query("count")int count);

//    Call<RestaurantResponse> getRestaurantCollection(@Query("user-key") String userKey, @Query("lat") String lat
//            , @Query("lon") String lon,@Query("count")String count);


}
