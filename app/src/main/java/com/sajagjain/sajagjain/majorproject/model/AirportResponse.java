package com.sajagjain.sajagjain.majorproject.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajag jain on 29-12-2017.
 */

public class AirportResponse {
    String iata,iso,name,lat,lon ,size,city;

    public AirportResponse() {
    }

    public AirportResponse(String iata, String iso, String name, String lat, String lon, String size,String city) {
        this.iata = iata;
        this.iso = iso;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.size = size;
        this.city=city;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static AirportResponse fromJson(JSONObject jsonObject)
    {
        AirportResponse b=new AirportResponse();
        try{
            b.iata=jsonObject.getString("iata");
            b.iso=jsonObject.getString("iso");
            b.lat=jsonObject.getString("lat");
            b.lon=jsonObject.getString("lon");
            b.name=jsonObject.getString("name");
            b.size=jsonObject.getString("size");
            b.city=jsonObject.getString("city");
        }catch(JSONException ex){}
        return b;
    }

    public static List<AirportResponse> fromJson(JSONArray jsonArray) {
        List<AirportResponse> list=new ArrayList<>();
        JSONObject jsonObject;
        for(int i=1;i<jsonArray.length();i++) {
            try {
                jsonObject=jsonArray.getJSONObject(i);

            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            AirportResponse step=AirportResponse.fromJson(jsonObject);
            if (step!=null){
                list.add(step);
            }

        }
        // Return new object
        return list;
    }
}
