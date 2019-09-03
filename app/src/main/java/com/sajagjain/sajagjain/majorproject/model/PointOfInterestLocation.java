package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 15-12-2017.
 */


public class PointOfInterestLocation implements Serializable {
    @SerializedName("location")
    PointOfInterestLocationSecond location;

    public PointOfInterestLocation(PointOfInterestLocationSecond location) {
        this.location = location;
    }

    public PointOfInterestLocation() {
    }

    public PointOfInterestLocationSecond getLocation() {
        return location;
    }

    public void setLocation(PointOfInterestLocationSecond location) {
        this.location = location;
    }

    public static PointOfInterestLocation fromJson(JSONObject jsonObject) {
        PointOfInterestLocation b= new PointOfInterestLocation();
        // Deserialize json into object fields
        try {

            JSONObject listing = jsonObject.getJSONObject("location");
            b.location=PointOfInterestLocationSecond.fromJson(listing);



        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

}
