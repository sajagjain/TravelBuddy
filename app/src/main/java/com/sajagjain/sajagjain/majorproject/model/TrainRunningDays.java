package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 09-10-2017.
 */

public class TrainRunningDays implements Serializable {
    @SerializedName("code")
    String trainDayCode;

    @SerializedName("runs")
    String trainRunsOnDay;

    public String getTrainDayCode() {
        return trainDayCode;
    }

    public void setTrainDayCode(String trainDayCode) {
        this.trainDayCode = trainDayCode;
    }

    public String getTrainRunsOnDay() {
        return trainRunsOnDay;
    }

    public void setTrainRunsOnDay(String trainRunsOnDay) {
        this.trainRunsOnDay = trainRunsOnDay;
    }
}
