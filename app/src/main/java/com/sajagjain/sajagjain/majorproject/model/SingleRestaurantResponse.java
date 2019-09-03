package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 20-12-2017.
 */

public class SingleRestaurantResponse implements Serializable {
    @SerializedName("restaurants")
    List<SingleRestaurantFirstStep> list;

    public SingleRestaurantResponse() {
    }

    public SingleRestaurantResponse(List<SingleRestaurantFirstStep> list) {
        this.list = list;
    }

    public List<SingleRestaurantFirstStep> getList() {
        return list;
    }

    public void setList(List<SingleRestaurantFirstStep> list) {
        this.list = list;
    }

    public static SingleRestaurantResponse fromJson(JSONObject jsonObject) {
        SingleRestaurantResponse b = new SingleRestaurantResponse();
        try {
            JSONArray listing = jsonObject.getJSONArray("restaurants");
            b.list = SingleRestaurantFirstStep.fromJson(listing);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }
}

