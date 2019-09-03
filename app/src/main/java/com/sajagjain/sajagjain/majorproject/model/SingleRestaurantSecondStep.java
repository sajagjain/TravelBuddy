package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 20-12-2017.
 */

public class SingleRestaurantSecondStep implements Serializable {
    @SerializedName("name")
    String name;
    @SerializedName("photos_url")
    String photoURL;
    @SerializedName("url")
    String redirectURL;
    @SerializedName("cuisines")
    String cuisines;
    @SerializedName("average_cost_for_two")
    String avgPriceForTwo;
    @SerializedName("currency")
    String currency;
    @SerializedName("user_rating")
    SingleRestaurantThirdStepOne ratings;
    @SerializedName("location")
    SingleRestaurantThirdStepTwo location;
    @SerializedName("menu_url")
    String menuURL;

    public SingleRestaurantSecondStep() {
    }

    public SingleRestaurantSecondStep(String name, String photoURL, String redirectURL, String cuisines, String avgPriceForTwo, String currency, SingleRestaurantThirdStepOne ratings, SingleRestaurantThirdStepTwo location, String menuURL) {
        this.name = name;
        this.photoURL = photoURL;
        this.redirectURL = redirectURL;
        this.cuisines = cuisines;
        this.avgPriceForTwo = avgPriceForTwo;
        this.currency = currency;
        this.ratings = ratings;
        this.location = location;
        this.menuURL = menuURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getAvgPriceForTwo() {
        return avgPriceForTwo;
    }

    public void setAvgPriceForTwo(String avgPriceForTwo) {
        this.avgPriceForTwo = avgPriceForTwo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public SingleRestaurantThirdStepOne getRatings() {
        return ratings;
    }

    public void setRatings(SingleRestaurantThirdStepOne ratings) {
        this.ratings = ratings;
    }

    public SingleRestaurantThirdStepTwo getLocation() {
        return location;
    }

    public void setLocation(SingleRestaurantThirdStepTwo location) {
        this.location = location;
    }

    public String getMenuURL() {
        return menuURL;
    }

    public void setMenuURL(String menuURL) {
        this.menuURL = menuURL;
    }


    public static SingleRestaurantSecondStep fromJson(JSONObject jsonObject) {
        SingleRestaurantSecondStep b=new SingleRestaurantSecondStep();
        try{
            b.name=jsonObject.getString("name");
            b.photoURL=jsonObject.getString("photos_url");
            b.redirectURL=jsonObject.getString("url");
            b.cuisines=jsonObject.getString("cuisines");
            b.avgPriceForTwo=jsonObject.getString("average_cost_for_two");
            b.currency=jsonObject.getString("currency");
            b.menuURL=jsonObject.getString("menu_url");
            JSONObject obj1=jsonObject.getJSONObject("user_rating");
            b.ratings=SingleRestaurantThirdStepOne.fromJson(obj1);
            JSONObject obj2=jsonObject.getJSONObject("location");
            b.location=SingleRestaurantThirdStepTwo.fromJson(obj2);

        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return b;

    }
}
