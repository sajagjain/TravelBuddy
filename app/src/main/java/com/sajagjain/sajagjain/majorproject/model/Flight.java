package com.sajagjain.sajagjain.majorproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sajag jain on 06-10-2017.
 */

public class Flight implements Serializable {

    private String flightSource = null, flightDestination = null;

    private String fightDepartureDate;

    @SerializedName("duration")
    private String duration;

    @SerializedName("fare")
    private FlightFare fightFareClass;

    @SerializedName("CINFO")
    private String redirectRequest;

    @SerializedName("deptime")
    private String departureTime;

    @SerializedName("depdate")
    private String departureDate;

    @SerializedName("seatsavailable")
    private String seatsAvailable;

    @SerializedName("airline")
    private String airline;

    @SerializedName("warnings")
    private String refundable;

    @SerializedName("seatingclass")
    private String seatingClass;

    @SerializedName("arrtime")
    private String arrivalTime;

    @SerializedName("arrdate")
    private String arrivalDate;

    public Flight(String departureDate,String redirectRequest,String duration, FlightFare fightFareClass, String departureTime, String seatsAvailable, String airline, String refundable, String seatingClass, String arrivalTime, String arrivalDate,String flightSource,String flightDestination) {
        this.departureDate=departureDate;
        this.redirectRequest=redirectRequest;
        this.duration = duration;
        this.fightFareClass = fightFareClass;
        this.departureTime = departureTime;
        this.seatsAvailable = seatsAvailable;
        this.airline = airline;
        this.refundable = refundable;
        this.seatingClass = seatingClass;
        this.arrivalTime = arrivalTime;
        this.arrivalDate = arrivalDate;
        this.flightSource=flightSource;
        this.flightDestination=flightDestination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getFightDepartureDate() {
        return fightDepartureDate;
    }

    public void setFightDepartureDate(String fightDepartureDate) {
        this.fightDepartureDate = fightDepartureDate;
    }

    public String getRedirectRequest() {
        return redirectRequest;
    }

    public void setRedirectRequest(String redirectRequest) {
        this.redirectRequest = redirectRequest;
    }

    public FlightFare getFightFareClass() {
        return fightFareClass;
    }

    public void setFightFareClass(FlightFare fightFareClass) {
        this.fightFareClass = fightFareClass;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getRefundable() {
        return refundable;
    }

    public void setRefundable(String refundable) {
        this.refundable = refundable;
    }

    public String getSeatingClass() {
        return seatingClass;
    }

    public void setSeatingClass(String seatingClass) {
        this.seatingClass = seatingClass;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getFlightSource() {
        return flightSource;
    }

    public void setFlightSource(String flightSource) {
        this.flightSource = flightSource;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = flightDestination;
    }
}
