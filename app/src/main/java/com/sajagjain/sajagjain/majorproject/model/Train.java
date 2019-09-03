package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by sajag jain on 09-10-2017.
 */

public class Train implements Serializable {

    private String trainSource=null,trainDestination=null;

    Date trainDate;

    @SerializedName("number")
    String trainNumber;

    @SerializedName("name")
    String trainName;

    @SerializedName("travel_time")
    String travelTime;

    @SerializedName("src_departure_time")
    String departureTime;

    @SerializedName("dest_arrival_time")
    String arrivalTime;

    @SerializedName("from_station")
    TrainFromStation fromStation;

    @SerializedName("to_station")
    TrainToStation toStation;

    @SerializedName("classes")
    List<TrainClass> trainClassList;

    @SerializedName("days")
    List<TrainRunningDays> trainRunningDaysList;

    public Train(String trainNumber, String trainName, String travelTime, String departureTime, String arrivalTime, TrainFromStation fromStation, TrainToStation toStation, List<TrainClass> trainClassList, List<TrainRunningDays> trainRunningDaysList,String trainSource,String trainDestination) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.travelTime = travelTime;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.trainClassList = trainClassList;
        this.trainRunningDaysList = trainRunningDaysList;
        this.trainSource=trainSource;
        this.trainDestination=trainDestination;
    }


    public Date getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public TrainFromStation getFromStation() {
        return fromStation;
    }

    public void setFromStation(TrainFromStation fromStation) {
        this.fromStation = fromStation;
    }

    public TrainToStation getToStation() {
        return toStation;
    }

    public void setToStation(TrainToStation toStation) {
        this.toStation = toStation;
    }

    public List<TrainClass> getTrainClassList() {
        return trainClassList;
    }

    public void setTrainClassList(List<TrainClass> trainClassList) {
        this.trainClassList = trainClassList;
    }

    public List<TrainRunningDays> getTrainRunningDaysList() {
        return trainRunningDaysList;
    }

    public void setTrainRunningDaysList(List<TrainRunningDays> trainRunningDaysList) {
        this.trainRunningDaysList = trainRunningDaysList;
    }

    public String getTrainSource() {
        return trainSource;
    }

    public void setTrainSource(String trainSource) {
        this.trainSource = trainSource;
    }

    public String getTrainDestination() {
        return trainDestination;
    }

    public void setTrainDestination(String trainDestination) {
        this.trainDestination = trainDestination;
    }
}

