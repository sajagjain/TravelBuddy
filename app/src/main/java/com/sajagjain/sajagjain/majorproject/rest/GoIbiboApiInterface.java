package com.sajagjain.sajagjain.majorproject.rest;



import com.sajagjain.sajagjain.majorproject.model.BusResponse;
import com.sajagjain.sajagjain.majorproject.model.FlightResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GoIbiboApiInterface {
    @GET("search/")
    Call<FlightResponse> getFlightGoIbibo(@Query("app_id") String appID, @Query("app_key") String appKey
            , @Query("format") String format, @Query("source") String source, @Query("destination") String destination
            , @Query("dateofdeparture") String dateOfDeparture, @Query("seatingclass") String seatingClass
            , @Query("adults") int adults, @Query("children") int children, @Query("counter") String counter);

    @GET("bus/search/")
    Call<BusResponse> getBusesGoIbibo(@Query("app_id") String appID, @Query("app_key") String appKey
            , @Query("format") String format, @Query("source") String source, @Query("destination") String destination
            , @Query("dateofdeparture") String dateOfDeparture);

    @GET("bus/seatmap/")
    Call<FlightResponse> getSeatAvailable(@Query("app_id") String appID, @Query("app_key") String appKey
            , @Query("format") String format, @Query("source") String source, @Query("destination") String destination
            , @Query("dateofdeparture") String dateOfDeparture);

    @GET("voyager/get_hotels_by_cityid/")
    Call<FlightResponse> getHotels(@Query("app_id") String appID, @Query("app_key") String appKey
            , @Query("city_id") String cityID);


}
