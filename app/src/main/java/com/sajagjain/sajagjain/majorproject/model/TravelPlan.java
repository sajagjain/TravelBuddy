package com.sajagjain.sajagjain.majorproject.model;

import java.io.Serializable;

/**
 * Created by sajag jain on 27-01-2018.
 */

public class TravelPlan implements Serializable{
    String planName;
    String travelSource;
    String travelDestination;
    String startDate;
    String endDate;
    long creationDate;
    OverAllDataForSavingInTPA dataForSavingInTPA;

//    Bitmap image;

    public TravelPlan() {
    }

    public TravelPlan(String planName, String travelSource, String travelDestination, String startDate, String endDate, long creationDate, OverAllDataForSavingInTPA dataForSavingInTPA) {
        this.planName = planName;
        this.travelSource = travelSource;
        this.travelDestination = travelDestination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creationDate = creationDate;
        this.dataForSavingInTPA = dataForSavingInTPA;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTravelSource() {
        return travelSource;
    }

    public void setTravelSource(String travelSource) {
        this.travelSource = travelSource;
    }

    public OverAllDataForSavingInTPA getDataForSavingInTPA() {
        return dataForSavingInTPA;
    }

    public void setDataForSavingInTPA(OverAllDataForSavingInTPA dataForSavingInTPA) {
        this.dataForSavingInTPA = dataForSavingInTPA;
    }

    public String getTravelDestination() {
        return travelDestination;
    }

    public void setTravelDestination(String travelDestination) {
        this.travelDestination = travelDestination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    //    public Bitmap getImage() {
//        return image;
//    }
//
//    public void setImage(Bitmap image) {
//        this.image = image;
//    }
}
