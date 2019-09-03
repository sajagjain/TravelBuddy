package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 06-10-2017.
 */

public class FlightFare implements Serializable {

    @SerializedName("grossamount")
    private String flightFare;

    public FlightFare(String flightFare) {
        this.flightFare = flightFare;
    }

    public String getFlightFare() {
        return flightFare;
    }

    public void setFlightFare(String flightFare) {
        this.flightFare = flightFare;
    }
}
