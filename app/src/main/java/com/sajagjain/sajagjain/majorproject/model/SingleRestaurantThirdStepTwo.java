package com.sajagjain.sajagjain.majorproject.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 20-12-2017.
 */

public class SingleRestaurantThirdStepTwo implements Serializable {
    @SerializedName("latitude")
    String latitude;
    @SerializedName("longitude")
    String longitude;
    @SerializedName("address")
    String address;

    public SingleRestaurantThirdStepTwo() {
    }

    public SingleRestaurantThirdStepTwo(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static SingleRestaurantThirdStepTwo fromJson(JSONObject jsonObject)
    {
        SingleRestaurantThirdStepTwo b=new SingleRestaurantThirdStepTwo();
        try{
            b.address=jsonObject.getString("address");
            b.latitude=jsonObject.getString("latitude");
            b.longitude=jsonObject.getString("longitude");
        }catch(Exception ex){
            Log.d("singleResThirdStepOne",ex.getMessage());
        }
        return b;
    }
}
