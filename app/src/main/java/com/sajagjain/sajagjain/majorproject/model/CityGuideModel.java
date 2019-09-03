package com.sajagjain.sajagjain.majorproject.model;

import java.io.Serializable;

/**
 * Created by sajag jain on 25-02-2018.
 */

public class CityGuideModel implements Serializable {

    Double lat;
    Double lon;
    String guideName;
    String guideCity;
    String guidePhoneNumber;
    String guideAddress;
    String residentSince;

    public CityGuideModel() {
    }

    public CityGuideModel(Double lat, Double lon,  String guideCity,String guideName, String guidePhoneNumber, String guideAddress, String residentSince) {
        this.lat = lat;
        this.lon = lon;
        this.guideName = guideName;
        this.guideCity = guideCity;
        this.guidePhoneNumber = guidePhoneNumber;
        this.guideAddress = guideAddress;
        this.residentSince = residentSince;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getGuideCity() {
        return guideCity;
    }

    public void setGuideCity(String guideCity) {
        this.guideCity = guideCity;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }


    public String getGuidePhoneNumber() {
        return guidePhoneNumber;
    }

    public void setGuidePhoneNumber(String guidePhoneNumber) {
        this.guidePhoneNumber = guidePhoneNumber;
    }

    public String getGuideAddress() {
        return guideAddress;
    }

    public void setGuideAddress(String guideAddress) {
        this.guideAddress = guideAddress;
    }

    public String getResidentSince() {
        return residentSince;
    }

    public void setResidentSince(String residentSince) {
        this.residentSince = residentSince;
    }
}
