package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 13-12-2017.
 */

public class PlaceDescriptionText implements Serializable {
    @SerializedName("*")
    String text;

    public PlaceDescriptionText() {
    }

    public PlaceDescriptionText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static PlaceDescriptionText fromJson(JSONObject jsonObject) {
        PlaceDescriptionText b= new PlaceDescriptionText();
        // Deserialize json into object fields
        try {
            b.text=jsonObject.getString("*");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }
}
