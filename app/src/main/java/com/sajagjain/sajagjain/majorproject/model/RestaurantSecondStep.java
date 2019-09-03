package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 19-12-2017.
 */

public class RestaurantSecondStep implements Serializable {
    @SerializedName("collection_id")
    String collectionID;
    @SerializedName("image_url")
    String imageURL;
    @SerializedName("url")
    String redirectURL;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("share_url")
    String shareURL;

    public RestaurantSecondStep(String collectionID, String imageURL, String redirectURL, String title, String description, String shareURL) {
        this.collectionID = collectionID;
        this.imageURL = imageURL;
        this.redirectURL = redirectURL;
        this.title = title;
        this.description = description;
        this.shareURL = shareURL;
    }

    public RestaurantSecondStep() {
    }

    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareURL() {
        return shareURL;
    }

    public void setShareURL(String shareURL) {
        this.shareURL = shareURL;
    }

    public static RestaurantSecondStep fromJson(JSONObject jsonObject){
        RestaurantSecondStep b=new RestaurantSecondStep();
        try{
            b.collectionID=jsonObject.getString("collection_id");
            b.description=jsonObject.getString("description");
            b.shareURL=jsonObject.getString("share_url");
            b.imageURL=jsonObject.getString("image_url");
            b.redirectURL=jsonObject.getString("url");
            b.title=jsonObject.getString("title");


        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return b;
    }
}
