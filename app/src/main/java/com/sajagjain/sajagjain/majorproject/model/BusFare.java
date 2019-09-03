package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 08-10-2017.
 */

public class BusFare implements Serializable{

    @SerializedName("SellFare")
    private String busFare;

    @SerializedName("busCondition")
    private String busCondition;

    @SerializedName("seatType")
    private String busSeatType;

    @SerializedName("SeatsAvailable")
    private String busSeatsAvailablity;

    public BusFare(String busFare, String busCondition, String busSeatType, String busSeatsAvailablity) {
        this.busFare = busFare;
        this.busCondition = busCondition;
        this.busSeatType = busSeatType;
        this.busSeatsAvailablity = busSeatsAvailablity;
    }

    public String getBusFare() {
        return busFare;
    }

    public void setBusFare(String busFare) {
        this.busFare = busFare;
    }

    public String getBusCondition() {
        return busCondition;
    }

    public void setBusCondition(String busCondition) {
        this.busCondition = busCondition;
    }

    public String getBusSeatType() {
        return busSeatType;
    }

    public void setBusSeatType(String busSeatType) {
        this.busSeatType = busSeatType;
    }

    public String getBusSeatsAvailablity() {
        return busSeatsAvailablity;
    }

    public void setBusSeatsAvailablity(String busSeatsAvailablity) {
        this.busSeatsAvailablity = busSeatsAvailablity;
    }
}
