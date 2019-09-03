package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 15-12-2017.
 */

public class PointOfInterestLocationSecond implements Serializable {
    @SerializedName("lat")
    String lat;
    @SerializedName("lng")
    String lng;

    public PointOfInterestLocationSecond(){}
    public PointOfInterestLocationSecond(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public static PointOfInterestLocationSecond fromJson(JSONObject jsonObject) {
        PointOfInterestLocationSecond b= new PointOfInterestLocationSecond();
        // Deserialize json into object fields
        try {

            b.lat = jsonObject.getString("lat");
            b.lng=jsonObject.getString("lng");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

}
