package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by sajag jain on 06-10-2017.
 */

public class FlightResponse implements Serializable {
    @SerializedName("data")
    private FlightFirstStep data;

    public FlightResponse(FlightFirstStep data) {
        this.data = data;
    }

    public FlightFirstStep getData() {
        return data;
    }

    public void setData(FlightFirstStep data) {
        this.data = data;
    }
}
