package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;

/**
 * Created by Victor on 29/03/2017.
 */

public class Seat implements Serializable {
    private String seatID;
    private int statusSeat;
    private String stLicense_Plate;

    public Seat(String seatID, int statusSeat, String stLicense_Plate) {
        this.seatID = seatID;
        this.statusSeat = statusSeat;
        this.stLicense_Plate = stLicense_Plate;
    }

    public Seat() {
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public int getStatusSeat() {
        return statusSeat;
    }

    public void setStatusSeat(int statusSeat) {
        this.statusSeat = statusSeat;
    }

    public String getStLicense_Plate() {
        return stLicense_Plate;
    }

    public void setStLicense_Plate(String stLicense_Plate) {
        this.stLicense_Plate = stLicense_Plate;
    }
}
