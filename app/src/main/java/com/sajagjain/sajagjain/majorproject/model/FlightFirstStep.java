package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 06-10-2017.
 */

public class FlightFirstStep implements Serializable {

    @SerializedName("onwardflights")
    private List<Flight> flightinterimdata;


    public List<Flight> getFlightinterimdata() {
        return flightinterimdata;
    }

    public void setFlightinterimdata(List<Flight> flightinterimdata) {
        this.flightinterimdata = flightinterimdata;
    }
}
