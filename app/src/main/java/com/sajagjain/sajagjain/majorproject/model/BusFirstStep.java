package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 08-10-2017.
 */

public class BusFirstStep implements Serializable {
    @SerializedName("onwardflights")
    private List<Bus> businterimdata;

    public List<Bus> getBusinterimdata() {
        return businterimdata;
    }

    public void setBusinterimdata(List<Bus> businterimdata) {
        this.businterimdata = businterimdata;
    }
}
