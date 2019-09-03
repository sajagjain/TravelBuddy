package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 02-10-2017.
 */

public class Weather implements Serializable {
    @SerializedName("dt_txt")
    private String date;
    @SerializedName("main")
    private MainReport main;
    @SerializedName("weather")
    private List<WeatherDescription> description;


    public Weather(String date, MainReport main, List<WeatherDescription> description) {
        this.date = date;
        this.main = main;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MainReport getMain() {
        return main;
    }

    public void setMain(MainReport main) {
        this.main = main;
    }

    public List<WeatherDescription> getDescription() {
        return description;
    }

    public void setDescription(List<WeatherDescription> description) {
        this.description = description;
    }
}
