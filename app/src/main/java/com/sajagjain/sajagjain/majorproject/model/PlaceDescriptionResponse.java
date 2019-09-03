package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 13-12-2017.
 */

public class PlaceDescriptionResponse implements Serializable {
    @SerializedName("parse")
    PlaceDescriptionFirstStep desc;

    public PlaceDescriptionResponse() {
    }

    public PlaceDescriptionResponse(PlaceDescriptionFirstStep desc) {
        this.desc = desc;
    }

    public PlaceDescriptionFirstStep getDesc() {
        return desc;
    }

    public void setDesc(PlaceDescriptionFirstStep desc) {
        this.desc = desc;
    }

    public static PlaceDescriptionResponse fromJson(JSONObject jsonObject) {
        PlaceDescriptionResponse b= new PlaceDescriptionResponse();
        // Deserialize json into object fields
        try {
            JSONObject listing=jsonObject.getJSONObject("parse");
            b.desc = PlaceDescriptionFirstStep.fromJson(listing);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }
}
