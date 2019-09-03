package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 15-12-2017.
 */

public class PointOfInterestFirstStep implements Serializable {
    @SerializedName("formatted_address")
    String locationAddress;
    @SerializedName("icon")
    String poiImage;
    @SerializedName("name")
    String poiName;
    @SerializedName("rating")
    String poiRating;
    @SerializedName("place_id")
    String poiPlaceID;
    @SerializedName("geometry")
    PointOfInterestLocation loc;
    @SerializedName("photos")
    List<PointOfInterestPhotos> photos;

    public PointOfInterestFirstStep() {
    }

    public PointOfInterestFirstStep(List<PointOfInterestPhotos> photos,String locationAddress, String poiImage, String poiName, String poiRating, String poiPlaceID, PointOfInterestLocation loc) {
        this.locationAddress = locationAddress;
        this.photos=photos;
        this.poiImage = poiImage;
        this.poiName = poiName;
        this.poiRating = poiRating;
        this.poiPlaceID = poiPlaceID;
        this.loc = loc;
    }

    public List<PointOfInterestPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PointOfInterestPhotos> photos) {
        this.photos = photos;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getPoiRating() {
        return poiRating;
    }

    public void setPoiRating(String poiRating) {
        this.poiRating = poiRating;
    }

    public String getPoiImage() {
        return poiImage;
    }

    public void setPoiImage(String poiImage) {
        this.poiImage = poiImage;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }


    public String getPoiPlaceID() {
        return poiPlaceID;
    }

    public void setPoiPlaceID(String poiPlaceID) {
        this.poiPlaceID = poiPlaceID;
    }

    public PointOfInterestLocation getLoc() {
        return loc;
    }

    public void setLoc(PointOfInterestLocation loc) {
        this.loc = loc;
    }

    public static PointOfInterestFirstStep fromJson(JSONObject jsonObject) {
        PointOfInterestFirstStep b= new PointOfInterestFirstStep();
        // Deserialize json into object fields
        try {
            b.locationAddress = jsonObject.getString("formatted_address");
            b.poiImage=jsonObject.getString("icon");
            b.poiName=jsonObject.getString("name");

            b.poiRating=jsonObject.getString("rating");
            if(b.poiRating==null){
                b.poiRating="NA";
            }

            b.poiPlaceID=jsonObject.getString("place_id");
            JSONObject listing = jsonObject.getJSONObject("geometry");
            b.loc=PointOfInterestLocation.fromJson(listing);
            JSONArray picListing=jsonObject.getJSONArray("photos");
            b.photos=PointOfInterestPhotos.fromJson(picListing);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    public static List<PointOfInterestFirstStep> fromJson(JSONArray jsonArray) {
        List<PointOfInterestFirstStep> list= new ArrayList<>();
        // Deserialize json into object fields
        JSONObject jsonObject;
        for(int i=0;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            PointOfInterestFirstStep step=PointOfInterestFirstStep.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        // Return new object
        return list;
    }

}
