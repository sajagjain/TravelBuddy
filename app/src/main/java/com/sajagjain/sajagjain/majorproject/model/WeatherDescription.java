package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 03-10-2017.
 */

public class WeatherDescription implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("main")
    String weatherStatusMain;
    @SerializedName("description")
    String weatherDescription;

    public WeatherDescription(String id, String weatherStatusMain, String weatherDescription) {
        this.id = id;
        this.weatherStatusMain = weatherStatusMain;
        this.weatherDescription = weatherDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeatherStatusMain() {
        return weatherStatusMain;
    }

    public void setWeatherStatusMain(String weatherStatusMain) {
        this.weatherStatusMain = weatherStatusMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
