package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;

/**
 * Created by Victor on 16/04/2017.
 */

public class TicketDetails implements Serializable {
    private String email;
    private Integer ticketID;
    private String telOfPassenger;
    private String nameOfPassenger;
    private String departureDate;
    private String boardingLocal;
    private String droppingLocal;
    private String seatID;
    private String departureTime;
    private String companyName;
    private String departureStation;
    private String arrivalStation;
    private int price;
    private String coachRouteID;
    private String orderID;

    public TicketDetails() {
    }

    public TicketDetails(String email, Integer ticketID, String telOfPassenger,
                         String nameOfPassenger, String departureDate, String boardingLocal,
                         String droppingLocal, String seatID, String departureTime,
                         String companyName, String departureStation, String arrivalStation,
                         int price, String coachRouteID, String orderID) {
        this.email = email;
        this.ticketID = ticketID;
        this.telOfPassenger = telOfPassenger;
        this.nameOfPassenger = nameOfPassenger;
        this.departureDate = departureDate;
        this.boardingLocal = boardingLocal;
        this.droppingLocal = droppingLocal;
        this.seatID = seatID;
        this.departureTime = departureTime;
        this.companyName = companyName;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.price = price;
        this.coachRouteID = coachRouteID;
        this.orderID = orderID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCoachRouteID() {
        return coachRouteID;
    }

    public void setCoachRouteID(String coachRouteID) {
        this.coachRouteID = coachRouteID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
