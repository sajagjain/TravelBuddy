package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 16-12-2017.
 */

public class PointOfInterestPhotos implements Serializable {
    @SerializedName("photo_reference")
    String photoReference;

    public PointOfInterestPhotos(String photoReference) {
        this.photoReference = photoReference;
    }

    public PointOfInterestPhotos() {
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public static PointOfInterestPhotos fromJson(JSONObject jsonObject) {
        PointOfInterestPhotos b= new PointOfInterestPhotos();
        // Deserialize json into object fields
        try {
            b.photoReference = jsonObject.getString("photo_reference");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    public static List<PointOfInterestPhotos> fromJson(JSONArray jsonArray) {
        List<PointOfInterestPhotos> list= new ArrayList<>();
        // Deserialize json into object fields
        JSONObject jsonObject;
        for(int i=0;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            PointOfInterestPhotos step=PointOfInterestPhotos.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        // Return new object
        return list;
    }


}
