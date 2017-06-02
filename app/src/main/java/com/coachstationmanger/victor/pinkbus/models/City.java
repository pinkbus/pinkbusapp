package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;

/**
 * Created by Victor on 01/04/2017.
 */

public class City implements Serializable {
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public City(String cityName) {
        this.cityName = cityName;
    }

    public City() {
    }
}
