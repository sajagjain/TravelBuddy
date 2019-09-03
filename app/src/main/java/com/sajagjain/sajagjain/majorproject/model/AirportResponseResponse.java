package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sajag jain on 29-12-2017.
 */

public class AirportResponseResponse {
    @SerializedName("results")
    List<AirportResponse> results;

    public AirportResponseResponse() {
    }

    public AirportResponseResponse(List<AirportResponse> results) {
        this.results = results;
    }

    public List<AirportResponse> getResults() {
        return results;
    }

    public void setResults(List<AirportResponse> results) {
        this.results = results;
    }

    public static AirportResponseResponse fromJson(JSONObject jsonObject)
    {
        AirportResponseResponse b=new AirportResponseResponse();
        try{

            b.results=AirportResponse.fromJson(jsonObject.getJSONArray("results"));

        }catch(JSONException ex){}
        return b;
    }
}
