package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 06-01-2018.
 */

public class DescribeMeEverythingFirstStep implements Serializable {

    @SerializedName("formatted_address")
    String address;
    @SerializedName("international_phone_number")
    String phn_number;
    @SerializedName("url")
    String mapUrl;
    @SerializedName("name")
    String name;
    @SerializedName("opening_hours")
    DescribeMeEverythingSecondStep secondStep;
    @SerializedName("rating")
    String overAllRating;
    @SerializedName("reviews")
    List<DescribeMeEverythingThirdStep> ratingModel;
    @SerializedName("website")
    String website;

    public DescribeMeEverythingFirstStep() {
    }

    public DescribeMeEverythingFirstStep(String mapUrl,String address, String phn_number, String name, DescribeMeEverythingSecondStep secondStep, String overAllRating, List<DescribeMeEverythingThirdStep> ratingModel, String website) {
        this.mapUrl=mapUrl;
        this.address = address;
        this.phn_number = phn_number;
        this.name = name;
        this.secondStep = secondStep;
        this.overAllRating = overAllRating;
        this.ratingModel = ratingModel;
        this.website = website;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhn_number() {
        return phn_number;
    }

    public void setPhn_number(String phn_number) {
        this.phn_number = phn_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DescribeMeEverythingSecondStep getSecondStep() {
        return secondStep;
    }

    public void setSecondStep(DescribeMeEverythingSecondStep secondStep) {
        this.secondStep = secondStep;
    }

    public String getOverAllRating() {
        return overAllRating;
    }

    public void setOverAllRating(String overAllRating) {
        this.overAllRating = overAllRating;
    }

    public List<DescribeMeEverythingThirdStep> getRatingModel() {
        return ratingModel;
    }

    public void setRatingModel(List<DescribeMeEverythingThirdStep> ratingModel) {
        this.ratingModel = ratingModel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public static DescribeMeEverythingFirstStep fromJson(JSONObject jsonObject)
    {
        DescribeMeEverythingFirstStep b=new DescribeMeEverythingFirstStep();
        try{
            b.mapUrl=jsonObject.getString("url");
            b.address=jsonObject.getString("formatted_address");
            b.name=jsonObject.getString("name");
            b.phn_number=jsonObject.getString("international_phone_number");
            b.secondStep=DescribeMeEverythingSecondStep.fromJson(jsonObject.getJSONObject("opening_hours"));
            b.overAllRating=jsonObject.getString("rating");
            b.ratingModel=DescribeMeEverythingThirdStep.fromJson(jsonObject.getJSONArray("reviews"));
            b.website=jsonObject.getString("website");

        }catch(Exception ex){}
        return b;
    }
//    public static List<DescribeMeEverythingFirstStep> fromJson(JSONArray jsonArray) {
//        List<DescribeMeEverythingFirstStep> list=new ArrayList<>();
//        JSONObject jsonObject;
//        for(int i=0;i<jsonArray.length();i++) {
//            try {
//                jsonObject=jsonArray.getJSONObject(i);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                continue;
//            }
//            DescribeMeEverythingFirstStep step=DescribeMeEverythingFirstStep.fromJson(jsonObject);
//            if (step!=null){
//                list.add(step);
//            }
//
//        }
//        return list;
//    }
}
