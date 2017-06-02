package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;

/**
 * Created by Victor on 28/03/2017.
 */

public class SeatDetails extends Seat implements Serializable {
    private String passengerName;
    private String telOfPassenger;
    private String boardingLocation;
    private String droppingLocation;

    public SeatDetails(String seatID, int statusSeat, String stLicense_Plate, String passengerName,
                       String telOfPassenger, String boardingLocation, String droppingLocation) {
        super(seatID, statusSeat, stLicense_Plate);
        this.passengerName = passengerName;
        this.telOfPassenger = telOfPassenger;
        this.boardingLocation = boardingLocation;
        this.droppingLocation = droppingLocation;
    }


    public SeatDetails() {
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getTelOfPassenger() {
        return telOfPassenger;
    }

    public void setTelOfPassenger(String telOfPassenger) {
        this.telOfPassenger = telOfPassenger;
    }

    public String getBoardingLocation() {
        return boardingLocation;
    }

    public void setBoardingLocation(String boardingLocation) {
        this.boardingLocation = boardingLocation;
    }

    public String getDroppingLocation() {
        return droppingLocation;
    }

    public void setDroppingLocation(String droppingLocation) {
        this.droppingLocation = droppingLocation;
    }
}
