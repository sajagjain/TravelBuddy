package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sajag jain on 29-12-2017.
 */

public class AirportNearByResponse {
    @SerializedName("response")
    List<AirportNearByResponseFirstStep> list;

    public AirportNearByResponse() {
    }

    public AirportNearByResponse(List<AirportNearByResponseFirstStep> list) {
        this.list = list;
    }

    public List<AirportNearByResponseFirstStep> getList() {
        return list;
    }

    public void setList(List<AirportNearByResponseFirstStep> list) {
        this.list = list;
    }


    public static AirportNearByResponse fromJson(JSONObject obj1) {
        AirportNearByResponse b=new AirportNearByResponse();
        try{
            JSONArray listing=obj1.getJSONArray("response");
            b.list=AirportNearByResponseFirstStep.fromJson(listing);
        }catch (JSONException e){}
        return b;
    }
}
