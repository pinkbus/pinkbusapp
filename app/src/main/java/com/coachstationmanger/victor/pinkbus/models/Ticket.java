package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;

/**
 * Created by Victor on 15/04/2017.
 */

public class Ticket implements Serializable {
    private Integer ticketID;
    private String telOfPassenger;
    private String nameOfPassenger;
    private String departureDate;
    private String boardingLocal;
    private String droppingLocal;
    private String seatID;
    private String coachRouteID;
    private String emailOfPassenger;
    private String paymentId;

    public Ticket(Integer ticketID, String telOfPassenger, String nameOfPassenger,
                  String departureDate, String boardingLocal, String droppingLocal, String seatID,
                  String coachRouteID, String emailOfPassenger, String paymentId) {
        this.ticketID = ticketID;
        this.telOfPassenger = telOfPassenger;
        this.nameOfPassenger = nameOfPassenger;
        this.departureDate = departureDate;
        this.boardingLocal = boardingLocal;
        this.droppingLocal = droppingLocal;
        this.seatID = seatID;
        this.coachRouteID = coachRouteID;
        this.emailOfPassenger = emailOfPassenger;
        this.paymentId = paymentId;
    }

    public Ticket() {
    }



    public String getTelOfPassenger() {
        return telOfPassenger;
    }

    public void setTelOfPassenger(String telOfPassenger) {
        this.telOfPassenger = telOfPassenger;
    }

    public String getNameOfPassenger() {
        return nameOfPassenger;
    }

    public void setNameOfPassenger(String nameOfPassenger) {
        this.nameOfPassenger = nameOfPassenger;
    }

    public String getBoardingLocal() {
        return boardingLocal;
    }

    public void setBoardingLocal(String boardingLocal) {
        this.boardingLocal = boardingLocal;
    }

    public String getDroppingLocal() {
        return droppingLocal;
    }

    public void setDroppingLocal(String droppingLocal) {
        this.droppingLocal = droppingLocal;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public String getCoachRouteID() {
        return coachRouteID;
    }

    public void setCoachRouteID(String coachRouteID) {
        this.coachRouteID = coachRouteID;
    }


    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getEmailOfPassenger() {
        return emailOfPassenger;
    }

    public void setEmailOfPassenger(String emailOfPassenger) {
        this.emailOfPassenger = emailOfPassenger;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
