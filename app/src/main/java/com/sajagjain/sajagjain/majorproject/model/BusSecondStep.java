package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 08-10-2017.
 */

public class BusSecondStep implements Serializable {
    @SerializedName("list")
    private List<BusFare> busList;

    public List<BusFare> getBusList() {
        return busList;
    }

    public void setBusList(List<BusFare> busList) {
        this.busList = busList;
    }
}
