package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 08-10-2017.
 */

public class Bus implements Serializable {

    private String busSource = null, busDestination = null;


    @SerializedName("duration")
    private String busDuration;

    @SerializedName("TravelsName")
    private String busTravelsName;

    @SerializedName("rating")
    private String busRating;


    @SerializedName("depdate")
    private String busDepartureTime;

    @SerializedName("amenities")
    private String busAmenities;

    @SerializedName("BusType")
    private String busType;

    @SerializedName("RouteSeatTypeDetail")
    private BusSecondStep busSubDetail;

    @SerializedName("src_voyager_id")
    private String srcVoyagerID;

    @SerializedName("dest_voyager_id")
    private String destVogayerID;

    @SerializedName("arrdate")
    private String busArrivalDate;

    public Bus(String srcVoyagerID, String destVogayerID, String busDuration, String busTravelsName, String busRating, String busDepartureTime, String busAmenities, String busType, BusSecondStep busSubDetail, String busArrivalDate, String busSource, String busDestination) {
        this.srcVoyagerID = srcVoyagerID;
        this.destVogayerID = destVogayerID;
        this.busDuration = busDuration;
        this.busTravelsName = busTravelsName;
        this.busRating = busRating;
        this.busDepartureTime = busDepartureTime;
        this.busAmenities = busAmenities;
        this.busType = busType;
        this.busSubDetail = busSubDetail;
        this.busArrivalDate = busArrivalDate;
        this.busSource = busSource;
        this.busDestination = busDestination;
    }

    public String getDestVogayerID() {
        return destVogayerID;
    }

    public void setDestVogayerID(String destVogayerID) {
        this.destVogayerID = destVogayerID;
    }

    public String getSrcVoyagerID() {
        return srcVoyagerID;
    }

    public void setSrcVoyagerID(String srcVoyagerID) {
        this.srcVoyagerID = srcVoyagerID;
    }

    public String getBusDuration() {
        return busDuration;
    }

    public void setBusDuration(String busDuration) {
        this.busDuration = busDuration;
    }

    public String getBusTravelsName() {
        return busTravelsName;
    }

    public void setBusTravelsName(String busTravelsName) {
        this.busTravelsName = busTravelsName;
    }

    public String getBusRating() {
        return busRating;
    }

    public void setBusRating(String busRating) {
        this.busRating = busRating;
    }

    public String getBusDepartureTime() {
        return busDepartureTime;
    }

    public void setBusDepartureTime(String busDepartureTime) {
        this.busDepartureTime = busDepartureTime;
    }

    public String getBusAmenities() {
        return busAmenities;
    }

    public void setBusAmenities(String busAmenities) {
        this.busAmenities = busAmenities;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public BusSecondStep getBusSubDetail() {
        return busSubDetail;
    }

    public void setBusSubDetail(BusSecondStep busSubDetail) {
        this.busSubDetail = busSubDetail;
    }

    public String getBusArrivalDate() {
        return busArrivalDate;
    }

    public void setBusArrivalDate(String busArrivalDate) {
        this.busArrivalDate = busArrivalDate;
    }

    public String getBusSource() {
        return busSource;
    }

    public void setBusSource(String busSource) {
        this.busSource = busSource;
    }

    public String getBusDestination() {
        return busDestination;
    }

    public void setBusDestination(String busDestination) {
        this.busDestination = busDestination;
    }
}
