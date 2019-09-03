package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 20-12-2017.
 */

public class SingleRestaurantFirstStep implements Serializable {

        @SerializedName("restaurant")
        SingleRestaurantSecondStep restaurant;

    public SingleRestaurantFirstStep() {
    }

    public SingleRestaurantFirstStep(SingleRestaurantSecondStep restaurant) {
        this.restaurant = restaurant;
    }

    public SingleRestaurantSecondStep getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(SingleRestaurantSecondStep restaurant) {
        this.restaurant = restaurant;
    }

    public static SingleRestaurantFirstStep fromJson(JSONObject jsonObject)
        {
            SingleRestaurantFirstStep b=new SingleRestaurantFirstStep();
            try{
                JSONObject listing=jsonObject.getJSONObject("restaurant");
                b.restaurant=SingleRestaurantSecondStep.fromJson(listing);

            }catch(JSONException ex){}
            return b;
        }

        public static List<SingleRestaurantFirstStep> fromJson(JSONArray jsonArray) {
            List<SingleRestaurantFirstStep> list=new ArrayList<>();
            JSONObject jsonObject;
            for(int i=0;i<jsonArray.length();i++) {
                try {
                    jsonObject=jsonArray.getJSONObject(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                    continue;
                }
                SingleRestaurantFirstStep step= SingleRestaurantFirstStep.fromJson(jsonObject);
                if (step!=null){
                    list.add(step);
                }

            }
            // Return new object
            return list;
        }

    }
