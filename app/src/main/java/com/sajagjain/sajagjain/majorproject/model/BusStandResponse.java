package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 05-01-2018.
 */

public class BusStandResponse implements Serializable {
    @SerializedName("buscities")
    List<BusStandFirstStep> list;

    public BusStandResponse() {
    }

    public BusStandResponse(List<BusStandFirstStep> list) {
        this.list = list;
    }

    public List<BusStandFirstStep> getList() {
        return list;
    }

    public void setList(List<BusStandFirstStep> list) {
        this.list = list;
    }

    public static BusStandResponse fromJson(JSONObject jsonObject)
    {
        BusStandResponse b=new BusStandResponse();
        try{

            b.list=BusStandFirstStep.fromJson(jsonObject.getJSONArray("buscities"));

        }catch(JSONException ex){}
        return b;
    }
}
