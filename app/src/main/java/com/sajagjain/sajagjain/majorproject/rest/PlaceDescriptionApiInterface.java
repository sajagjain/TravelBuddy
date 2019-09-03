package com.sajagjain.sajagjain.majorproject.rest;

import com.sajagjain.sajagjain.majorproject.model.PlaceDescriptionResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sajag jain on 13-12-2017.
 */

public interface PlaceDescriptionApiInterface {
    @POST("api.php")
    Call<PlaceDescriptionResponse> getPlaceDescription(@Query("action") String action, @Query("page") String pageName
            , @Query("prop") String prop
            , @Query("format") String format, @Query("mobileformat") boolean mobileformat,@Query("callback") String callback);

}
