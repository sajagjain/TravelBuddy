package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 04-01-2018.
 */

public class TrainStationFirstStep implements Serializable {
    @SerializedName("station")
    String station;
    @SerializedName("station_code")
    String stationCode;
    @SerializedName("state")
    String stationState;

    public TrainStationFirstStep() {
    }

    public TrainStationFirstStep(String station, String stationCode, String stationState) {
        this.station = station;
        this.stationCode = stationCode;
        this.stationState = stationState;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationState() {
        return stationState;
    }

    public void setStationState(String stationState) {
        this.stationState = stationState;
    }


    public static TrainStationFirstStep fromJson(JSONObject jsonObject)
    {
        TrainStationFirstStep b=new TrainStationFirstStep();
        try{
            b.station=jsonObject.getString("station");
            b.stationCode=jsonObject.getString("station_code");
            b.stationState=jsonObject.getString("state");
        }catch(JSONException ex){}
        return b;
    }

    public static List<TrainStationFirstStep> fromJson(JSONArray jsonArray) {
        List<TrainStationFirstStep> list=new ArrayList<>();
        JSONObject jsonObject;
        for(int i=1;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            TrainStationFirstStep step=TrainStationFirstStep.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        // Return new object
        return list;
    }
}
