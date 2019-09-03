package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 08-10-2017.
 */

public class BusResponse implements Serializable {
    @SerializedName("data")
    private BusFirstStep data;

    public BusResponse(BusFirstStep data) {
        this.data = data;
    }

    public BusFirstStep getData() {
        return data;
    }

    public void setData(BusFirstStep data) {
        this.data = data;
    }
}
