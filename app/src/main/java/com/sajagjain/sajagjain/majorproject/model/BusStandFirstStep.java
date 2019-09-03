package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 05-01-2018.
 */

public class BusStandFirstStep implements Serializable {

    @SerializedName("name")
    String busStandCityName;
    @SerializedName("state")
    String busStandCityState;

    public BusStandFirstStep() {
    }

    public BusStandFirstStep(String busStandCityName, String busStandCityState) {
        this.busStandCityName = busStandCityName;
        this.busStandCityState = busStandCityState;
    }

    public String getBusStandCityName() {
        return busStandCityName;
    }

    public void setBusStandCityName(String busStandCityName) {
        this.busStandCityName = busStandCityName;
    }

    public String getBusStandCityState() {
        return busStandCityState;
    }

    public void setBusStandCityState(String busStandCityState) {
        this.busStandCityState = busStandCityState;
    }


    public static BusStandFirstStep fromJson(JSONObject jsonObject)
    {
        BusStandFirstStep b=new BusStandFirstStep();
        try{
            b.busStandCityName=jsonObject.getString("name");
            b.busStandCityState=jsonObject.getString("state");
        }catch(JSONException ex){}
        return b;
    }

    public static List<BusStandFirstStep> fromJson(JSONArray jsonArray) {
        List<BusStandFirstStep> list=new ArrayList<>();
        JSONObject jsonObject;
        for(int i=1;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            BusStandFirstStep step=BusStandFirstStep.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        // Return new object
        return list;
    }

}
