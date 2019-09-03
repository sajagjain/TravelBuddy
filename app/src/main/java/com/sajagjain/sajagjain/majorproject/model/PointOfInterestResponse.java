package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 15-12-2017.
 */

public class PointOfInterestResponse implements Serializable {
    @SerializedName("results")
    List<PointOfInterestFirstStep> list;
    @SerializedName("status")
    String status;

    public PointOfInterestResponse() {
    }

    public PointOfInterestResponse(List<PointOfInterestFirstStep> list, String status) {
        this.list = list;
        this.status = status;
    }

    public List<PointOfInterestFirstStep> getList() {
        return list;
    }

    public void setList(List<PointOfInterestFirstStep> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static PointOfInterestResponse fromJson(JSONObject jsonObject) {
        PointOfInterestResponse b= new PointOfInterestResponse();
        // Deserialize json into object fields
        try {
            b.status = jsonObject.getString("status");
            JSONArray listing = jsonObject.getJSONArray("results");
            b.list=PointOfInterestFirstStep.fromJson(listing);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }
}
