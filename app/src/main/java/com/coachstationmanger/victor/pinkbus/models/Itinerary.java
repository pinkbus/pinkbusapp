package com.coachstationmanger.victor.pinkbus.models;

/**
 * Created by Victor on 24/03/2017.
 */

public class Itinerary {
    public Itinerary(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    private String itineraryName;

    public String getItineraryName() {
        return itineraryName;
    }

    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    public Itinerary() {
    }
    @Override
    public String toString()
    {
        return getItineraryName();
    }
}
