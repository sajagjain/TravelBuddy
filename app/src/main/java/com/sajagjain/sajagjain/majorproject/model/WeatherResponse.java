package com.sajagjain.sajagjain.majorproject.model;

/**
 * Created by sajag jain on 02-10-2017.
 */


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class WeatherResponse implements Serializable {

    @SerializedName("cod")
    private int statusCode;
    @SerializedName("list")
    private List<Weather> results;
    @SerializedName("cnt")
    private int totalResults;
    @SerializedName("message")
    private double message;

    public WeatherResponse(int statusCode, List<Weather> results, int totalResults, double message) {
        this.statusCode = statusCode;
        this.results = results;
        this.totalResults = totalResults;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<Weather> getResults() {
        return results;
    }

    public void setResults(List<Weather> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }
}
