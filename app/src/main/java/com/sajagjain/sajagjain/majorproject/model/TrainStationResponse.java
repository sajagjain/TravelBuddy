package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 04-01-2018.
 */

public class TrainStationResponse implements Serializable {
    @SerializedName("list")
    List<TrainStationFirstStep> stations;

    public TrainStationResponse() {
    }

    public TrainStationResponse(List<TrainStationFirstStep> stations) {
        this.stations = stations;
    }

    public List<TrainStationFirstStep> getStations() {
        return stations;
    }

    public void setStations(List<TrainStationFirstStep> stations) {
        this.stations = stations;
    }

    public static TrainStationResponse fromJson(JSONObject jsonObject)
    {
        TrainStationResponse b=new TrainStationResponse();
        try{

            b.stations=TrainStationFirstStep.fromJson(jsonObject.getJSONArray("list"));

        }catch(JSONException ex){}
        return b;
    }

}

