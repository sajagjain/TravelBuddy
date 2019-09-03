package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 06-01-2018.
 */

public class DescribeMeEverythingThirdStep implements Serializable {
    @SerializedName("author_name")
    String reviewAuthorName;
    @SerializedName("profile_photo_url")
    String reviewAuthorProfilePhotoUrl;
    @SerializedName("rating")
    String reviewAuthorRating;
    @SerializedName("relative_time_description")
    String reviewAuthorRelativeTimeDescription;
    @SerializedName("text")
    String reviewAuthorReview;
    @SerializedName("time")
    String reviewAuthorTimeInMillis;

    public DescribeMeEverythingThirdStep() {
    }

    public DescribeMeEverythingThirdStep(String reviewAuthorName, String reviewAuthorProfilePhotoUrl, String reviewAuthorRating, String reviewAuthorRelativeTimeDescription, String reviewAuthorReview, String reviewAuthorTimeInMillis) {
        this.reviewAuthorName = reviewAuthorName;
        this.reviewAuthorProfilePhotoUrl = reviewAuthorProfilePhotoUrl;
        this.reviewAuthorRating = reviewAuthorRating;
        this.reviewAuthorRelativeTimeDescription = reviewAuthorRelativeTimeDescription;
        this.reviewAuthorReview = reviewAuthorReview;
        this.reviewAuthorTimeInMillis = reviewAuthorTimeInMillis;
    }

    public String getReviewAuthorName() {
        return reviewAuthorName;
    }

    public void setReviewAuthorName(String reviewAuthorName) {
        this.reviewAuthorName = reviewAuthorName;
    }

    public String getReviewAuthorProfilePhotoUrl() {
        return reviewAuthorProfilePhotoUrl;
    }

    public void setReviewAuthorProfilePhotoUrl(String reviewAuthorProfilePhotoUrl) {
        this.reviewAuthorProfilePhotoUrl = reviewAuthorProfilePhotoUrl;
    }

    public String getReviewAuthorRating() {
        return reviewAuthorRating;
    }

    public void setReviewAuthorRating(String reviewAuthorRating) {
        this.reviewAuthorRating = reviewAuthorRating;
    }

    public String getReviewAuthorRelativeTimeDescription() {
        return reviewAuthorRelativeTimeDescription;
    }

    public void setReviewAuthorRelativeTimeDescription(String reviewAuthorRelativeTimeDescription) {
        this.reviewAuthorRelativeTimeDescription = reviewAuthorRelativeTimeDescription;
    }

    public String getReviewAuthorReview() {
        return reviewAuthorReview;
    }

    public void setReviewAuthorReview(String reviewAuthorReview) {
        this.reviewAuthorReview = reviewAuthorReview;
    }

    public String getReviewAuthorTimeInMillis() {
        return reviewAuthorTimeInMillis;
    }

    public void setReviewAuthorTimeInMillis(String reviewAuthorTimeInMillis) {
        this.reviewAuthorTimeInMillis = reviewAuthorTimeInMillis;
    }

    public static DescribeMeEverythingThirdStep fromJson(JSONObject jsonObject)
    {
        DescribeMeEverythingThirdStep b=new DescribeMeEverythingThirdStep();
        try{
            b.reviewAuthorName=jsonObject.getString("author_name");
            b.reviewAuthorProfilePhotoUrl=jsonObject.getString("profile_photo_url");
            b.reviewAuthorRating=jsonObject.getString("rating");
            b.reviewAuthorRelativeTimeDescription=jsonObject.getString("relative_time_description");
            b.reviewAuthorReview=jsonObject.getString("text");
            b.reviewAuthorTimeInMillis=jsonObject.getString("time");
        }catch (JSONException e){}
        return b;
    }
    public static List<DescribeMeEverythingThirdStep> fromJson(JSONArray jsonArray) {
        List<DescribeMeEverythingThirdStep> list=new ArrayList<>();
        JSONObject jsonObject;
        for(int i=0;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            DescribeMeEverythingThirdStep step=DescribeMeEverythingThirdStep.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        return list;
    }
}
