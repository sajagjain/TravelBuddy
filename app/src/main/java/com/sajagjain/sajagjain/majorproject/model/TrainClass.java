package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 09-10-2017.
 */

public class TrainClass implements Serializable {
    @SerializedName("code")
    String trainClassCode;

    @SerializedName("available")
    String trainClassAvailability;

    @SerializedName("name")
    String trainClassName;

    public String getTrainClassCode() {
        return trainClassCode;
    }

    public void setTrainClassCode(String trainClassCode) {
        this.trainClassCode = trainClassCode;
    }

    public String getTrainClassAvailability() {
        return trainClassAvailability;
    }

    public void setTrainClassAvailability(String trainClassAvailability) {
        this.trainClassAvailability = trainClassAvailability;
    }

    public String getTrainClassName() {
        return trainClassName;
    }

    public void setTrainClassName(String trainClassName) {
        this.trainClassName = trainClassName;
    }
}
