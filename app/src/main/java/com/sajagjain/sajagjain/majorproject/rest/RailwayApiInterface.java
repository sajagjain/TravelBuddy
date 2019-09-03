package com.sajagjain.sajagjain.majorproject.rest;



import com.sajagjain.sajagjain.majorproject.model.TrainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sajag jain on 09-10-2017.
 */

public interface RailwayApiInterface {

    @GET("between/source/{source}/dest/{dest}/date/{date}/apikey/biwd5zczxr/")
    Call<TrainResponse> getTrainInfo(@Path("source") String source, @Path("dest") String dest, @Path("date") String date);

}
