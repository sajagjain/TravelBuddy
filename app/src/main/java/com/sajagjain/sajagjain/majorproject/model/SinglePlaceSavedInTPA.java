package com.sajagjain.sajagjain.majorproject.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;

/**
 * Created by sajag jain on 25-01-2018.
 */

public class SinglePlaceSavedInTPA implements Serializable {

    String placeName;
    LatLng placeLatLng;
    Date visitDate;
    List<PointOfInterestFirstStep> selectedPointOfInterest;
    List<PointOfInterestFirstStep> selectedHotels;
    List<PointOfInterestFirstStep> selectedOyoRooms;
    List<SingleRestaurantFirstStep> selectedRestaurants;
    ArrayList<String> wikiDescribe;
    List<Status> tweets;

    public SinglePlaceSavedInTPA() {
    }


    public SinglePlaceSavedInTPA(LatLng placeLatLng,String placeName, List<PointOfInterestFirstStep> selectedPointOfInterest, List<PointOfInterestFirstStep> selectedHotels, List<PointOfInterestFirstStep> selectedOyoRooms, List<SingleRestaurantFirstStep> selectedRestaurants, ArrayList<String> wikiDescribe, List<Status> tweets) {
        this.placeLatLng=placeLatLng;
        this.placeName = placeName;
        this.selectedPointOfInterest = selectedPointOfInterest;
        this.selectedHotels = selectedHotels;
        this.selectedOyoRooms = selectedOyoRooms;
        this.selectedRestaurants = selectedRestaurants;
        this.wikiDescribe = wikiDescribe;
        this.tweets = tweets;
    }

    public LatLng getPlaceLatLng() {
        return placeLatLng;
    }

    public void setPlaceLatLng(LatLng placeLatLng) {
        this.placeLatLng = placeLatLng;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<PointOfInterestFirstStep> getSelectedPointOfInterest() {
        return selectedPointOfInterest;
    }

    public void setSelectedPointOfInterest(List<PointOfInterestFirstStep> selectedPointOfInterest) {
        this.selectedPointOfInterest = selectedPointOfInterest;
    }

    public List<PointOfInterestFirstStep> getSelectedHotels() {
        return selectedHotels;
    }

    public void setSelectedHotels(List<PointOfInterestFirstStep> selectedHotels) {
        this.selectedHotels = selectedHotels;
    }

    public List<PointOfInterestFirstStep> getSelectedOyoRooms() {
        return selectedOyoRooms;
    }

    public void setSelectedOyoRooms(List<PointOfInterestFirstStep> selectedOyoRooms) {
        this.selectedOyoRooms = selectedOyoRooms;
    }

    public List<SingleRestaurantFirstStep> getSelectedRestaurants() {
        return selectedRestaurants;
    }

    public void setSelectedRestaurants(List<SingleRestaurantFirstStep> selectedRestaurants) {
        this.selectedRestaurants = selectedRestaurants;
    }

    public ArrayList<String> getWikiDescribe() {
        return wikiDescribe;
    }

    public void setWikiDescribe(ArrayList<String> wikiDescribe) {
        this.wikiDescribe = wikiDescribe;
    }

    public List<Status> getTweets() {
        return tweets;
    }

    public void setTweets(List<Status> tweets) {
        this.tweets = tweets;
    }
}
