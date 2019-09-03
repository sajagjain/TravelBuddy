package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 13-12-2017.
 */

public class PlaceDescriptionFirstStep implements Serializable {
    @SerializedName("title")
    String title;
    @SerializedName("text")
    PlaceDescriptionText textContent;

    public PlaceDescriptionFirstStep(String title, PlaceDescriptionText textContent) {
        this.title = title;
        this.textContent = textContent;
    }

    public PlaceDescriptionFirstStep() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PlaceDescriptionText getTextContent() {
        return textContent;
    }

    public void setTextContent(PlaceDescriptionText textContent) {
        this.textContent = textContent;
    }


    public static PlaceDescriptionFirstStep fromJson(JSONObject jsonObject) {
        PlaceDescriptionFirstStep b= new PlaceDescriptionFirstStep();
        // Deserialize json into object fields
        try {
            b.title=jsonObject.getString("title");
            JSONObject listing=jsonObject.getJSONObject("text");
            b.textContent = PlaceDescriptionText.fromJson(listing);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

}
