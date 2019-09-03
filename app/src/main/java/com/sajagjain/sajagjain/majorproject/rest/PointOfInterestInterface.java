package com.sajagjain.sajagjain.majorproject.rest;

import com.sajagjain.sajagjain.majorproject.model.PointOfInterestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sajag jain on 15-12-2017.
 */

public interface PointOfInterestInterface {
    @GET("textsearch/json")
    Call<PointOfInterestResponse> getPointOfInterest(@Query("query") String query, @Query("key") String key);

}
