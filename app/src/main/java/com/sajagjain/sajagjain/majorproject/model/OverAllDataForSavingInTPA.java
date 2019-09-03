package com.sajagjain.sajagjain.majorproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 25-01-2018.
 */

public class OverAllDataForSavingInTPA implements Serializable {

    List<SinglePlaceSavedInTPA> savedPlaces;
    List<Flight> selectedFlights;
    List<Train> selectedTrains;
    List<Bus> selectedBuses;

    public OverAllDataForSavingInTPA(List<SinglePlaceSavedInTPA> savedPlaces, List<Flight> selectedFlights, List<Train> selectedTrains, List<Bus> selectedBuses) {
        this.savedPlaces = savedPlaces;
        this.selectedFlights = selectedFlights;
        this.selectedTrains = selectedTrains;
        this.selectedBuses = selectedBuses;
    }

    public OverAllDataForSavingInTPA() {
    }

    public List<SinglePlaceSavedInTPA> getSavedPlaces() {
        return savedPlaces;
    }

    public void setSavedPlaces(List<SinglePlaceSavedInTPA> savedPlaces) {
        this.savedPlaces = savedPlaces;
    }

    public List<Flight> getSelectedFlights() {
        return selectedFlights;
    }

    public void setSelectedFlights(List<Flight> selectedFlights) {
        this.selectedFlights = selectedFlights;
    }

    public List<Train> getSelectedTrains() {
        return selectedTrains;
    }

    public void setSelectedTrains(List<Train> selectedTrains) {
        this.selectedTrains = selectedTrains;
    }

    public List<Bus> getSelectedBuses() {
        return selectedBuses;
    }

    public void setSelectedBuses(List<Bus> selectedBuses) {
        this.selectedBuses = selectedBuses;
    }
}
