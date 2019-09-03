package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sajag jain on 06-01-2018.
 */

public class DescribeMeEverythingSecondStep implements Serializable {
    @SerializedName("open_now")
    String openNow;
    @SerializedName("weekday_text")
    String weekdaysInfoMonday;
    String weekdaysInfoTuesday;
    String weekdaysInfoWednesday;
    String weekdaysInfoThursday;
    String weekdaysInfoFriday;
    String weekdaysInfoSaturday;
    String weekdaysInfoSunday;

    public DescribeMeEverythingSecondStep() {
    }

    public DescribeMeEverythingSecondStep(String openNow, String weekdaysInfoMonday, String weekdaysInfoTuesday, String weekdaysInfoWednesday, String weekdaysInfoThursday, String weekdaysInfoFriday, String weekdaysInfoSaturday, String weekdaysInfoSunday) {
        this.openNow = openNow;
        this.weekdaysInfoMonday = weekdaysInfoMonday;
        this.weekdaysInfoTuesday = weekdaysInfoTuesday;
        this.weekdaysInfoWednesday = weekdaysInfoWednesday;
        this.weekdaysInfoThursday = weekdaysInfoThursday;
        this.weekdaysInfoFriday = weekdaysInfoFriday;
        this.weekdaysInfoSaturday = weekdaysInfoSaturday;
        this.weekdaysInfoSunday = weekdaysInfoSunday;
    }

    public String getOpenNow() {
        return openNow;
    }

    public void setOpenNow(String openNow) {
        this.openNow = openNow;
    }

    public String getWeekdaysInfoMonday() {
        return weekdaysInfoMonday;
    }

    public void setWeekdaysInfoMonday(String weekdaysInfoMonday) {
        this.weekdaysInfoMonday = weekdaysInfoMonday;
    }

    public String getWeekdaysInfoTuesday() {
        return weekdaysInfoTuesday;
    }

    public void setWeekdaysInfoTuesday(String weekdaysInfoTuesday) {
        this.weekdaysInfoTuesday = weekdaysInfoTuesday;
    }

    public String getWeekdaysInfoWednesday() {
        return weekdaysInfoWednesday;
    }

    public void setWeekdaysInfoWednesday(String weekdaysInfoWednesday) {
        this.weekdaysInfoWednesday = weekdaysInfoWednesday;
    }

    public String getWeekdaysInfoThursday() {
        return weekdaysInfoThursday;
    }

    public void setWeekdaysInfoThursday(String weekdaysInfoThursday) {
        this.weekdaysInfoThursday = weekdaysInfoThursday;
    }

    public String getWeekdaysInfoFriday() {
        return weekdaysInfoFriday;
    }

    public void setWeekdaysInfoFriday(String weekdaysInfoFriday) {
        this.weekdaysInfoFriday = weekdaysInfoFriday;
    }

    public String getWeekdaysInfoSaturday() {
        return weekdaysInfoSaturday;
    }

    public void setWeekdaysInfoSaturday(String weekdaysInfoSaturday) {
        this.weekdaysInfoSaturday = weekdaysInfoSaturday;
    }

    public String getWeekdaysInfoSunday() {
        return weekdaysInfoSunday;
    }

    public void setWeekdaysInfoSunday(String weekdaysInfoSunday) {
        this.weekdaysInfoSunday = weekdaysInfoSunday;
    }

    public static DescribeMeEverythingSecondStep fromJson(JSONObject jsonObject) {
        DescribeMeEverythingSecondStep b = new DescribeMeEverythingSecondStep();
        try {
            b.weekdaysInfoMonday = jsonObject.getJSONArray("weekday_text").get(0).toString();
            b.weekdaysInfoTuesday = jsonObject.getJSONArray("weekday_text").get(1).toString();
            b.weekdaysInfoWednesday = jsonObject.getJSONArray("weekday_text").get(2).toString();
            b.weekdaysInfoThursday = jsonObject.getJSONArray("weekday_text").get(3).toString();
            b.weekdaysInfoFriday = jsonObject.getJSONArray("weekday_text").get(4).toString();
            b.weekdaysInfoSaturday = jsonObject.getJSONArray("weekday_text").get(5).toString();
            b.weekdaysInfoSunday = jsonObject.getJSONArray("weekday_text").get(6).toString();
            b.openNow=jsonObject.getString("open_now");

        } catch (Exception e) {
        }
        return b;
    }
}
