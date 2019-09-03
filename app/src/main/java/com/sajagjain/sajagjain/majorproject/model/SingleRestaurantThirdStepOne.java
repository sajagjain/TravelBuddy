package com.sajagjain.sajagjain.majorproject.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 20-12-2017.
 */

public class SingleRestaurantThirdStepOne implements Serializable {
    @SerializedName("aggregate_rating")
    String aggregateRating;
    @SerializedName("rating_text")
    String ratingText;
    @SerializedName("votes")
    String votes;

    public SingleRestaurantThirdStepOne() {
    }

    public SingleRestaurantThirdStepOne(String aggregateRating, String ratingText, String votes) {
        this.aggregateRating = aggregateRating;
        this.ratingText = ratingText;
        this.votes = votes;
    }

    public String getAggregateRating() {
        return aggregateRating;
    }

    public void setAggregateRating(String aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public static SingleRestaurantThirdStepOne fromJson(JSONObject jsonObject)
    {
        SingleRestaurantThirdStepOne b=new SingleRestaurantThirdStepOne();
        try{
            b.aggregateRating=jsonObject.getString("aggregate_rating");
            b.ratingText=jsonObject.getString("rating_text");
            b.votes=jsonObject.getString("votes");
        }catch (Exception ex){
            Log.d("SigleResThirdStepOne",ex.getMessage());
        }
        return b;
    }
}
