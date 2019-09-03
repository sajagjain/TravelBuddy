package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 06-01-2018.
 */

public class DescribeMeEverythingResponse implements Serializable {
    @SerializedName("result")
    DescribeMeEverythingFirstStep describe;

    @SerializedName("status")
    String status;

    public DescribeMeEverythingResponse() {
    }

    public DescribeMeEverythingResponse(DescribeMeEverythingFirstStep describe, String status) {
        this.describe = describe;
        this.status = status;
    }

    public DescribeMeEverythingFirstStep getDescribe() {
        return describe;
    }

    public void setDescribe(DescribeMeEverythingFirstStep describe) {
        this.describe = describe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public static DescribeMeEverythingResponse fromJson(JSONObject jsonObject){
        DescribeMeEverythingResponse b=new DescribeMeEverythingResponse();
        try{
            b.status=jsonObject.getString("status");
            b.describe= DescribeMeEverythingFirstStep.fromJson(jsonObject.getJSONObject("result"));

        }catch(JSONException e){}
        return b;
    }
}
