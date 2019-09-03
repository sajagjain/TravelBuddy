package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 19-12-2017.
 */

public class RestaurantFirstStep implements Serializable {
    @SerializedName("collection")
    RestaurantSecondStep restaurant;

    public RestaurantFirstStep() {
    }

    public RestaurantFirstStep(RestaurantSecondStep restaurant) {
        this.restaurant = restaurant;
    }

    public RestaurantSecondStep getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantSecondStep restaurant) {
        this.restaurant = restaurant;
    }

    public static RestaurantFirstStep fromJson(JSONObject jsonObject)
    {
        RestaurantFirstStep b=new RestaurantFirstStep();
        try{
            JSONObject listing=jsonObject.getJSONObject("collection");
            b.restaurant=RestaurantSecondStep.fromJson(listing);

        }catch(JSONException ex){}
        return b;
    }

    public static List<RestaurantFirstStep> fromJson(JSONArray jsonArray) {
        List<RestaurantFirstStep> list=new ArrayList<>();
        JSONObject jsonObject;
        for(int i=0;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            RestaurantFirstStep step=RestaurantFirstStep.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        // Return new object
        return list;
    }
}
