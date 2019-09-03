package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 29-12-2017.
 */

public class AirportNearByResponseFirstStep {

    @SerializedName("name")
    String name;
    @SerializedName("code")
    String iataCode;
    @SerializedName("country_name")
    String countryName;

    public AirportNearByResponseFirstStep() {
    }

    public AirportNearByResponseFirstStep(String name, String iataCode, String countryName) {
        this.name = name;
        this.iataCode = iataCode;
        this.countryName = countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public static AirportNearByResponseFirstStep fromJson(JSONObject jsonObject) {
        AirportNearByResponseFirstStep b = new AirportNearByResponseFirstStep();
        try {
            b.name = jsonObject.getString("name");
            b.iataCode = jsonObject.getString("code");
            b.countryName = jsonObject.getString("country_name");
        } catch (JSONException e) {
        }
        return b;
    }

    public static List<AirportNearByResponseFirstStep> fromJson(JSONArray jsonArray) {
        List<AirportNearByResponseFirstStep> list = new ArrayList<>();
        JSONObject jsonObject;
        for(int i=0;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            AirportNearByResponseFirstStep step= AirportNearByResponseFirstStep.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        return list;
    }
}
