package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 18-12-2017.
 */

public class RestaurantResponse implements Serializable {
    @SerializedName("collections")
    List<RestaurantFirstStep> list;

    public RestaurantResponse() {

    }

    public RestaurantResponse(List<RestaurantFirstStep> list) {
        this.list = list;
    }

    public List<RestaurantFirstStep> getList() {
        return list;
    }

    public void setList(List<RestaurantFirstStep> list) {
        this.list = list;
    }

    public static RestaurantResponse fromJson(JSONObject jsonObject){
            RestaurantResponse b = new RestaurantResponse();
        try {
            JSONArray listing = jsonObject.getJSONArray("collections");
            b.list=RestaurantFirstStep.fromJson(listing);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }
}
