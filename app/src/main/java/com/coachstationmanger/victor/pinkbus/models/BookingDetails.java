package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 28/03/2017.
 */

public class BookingDetails extends BookingPerson implements Serializable {
    private String departureStation;
    private String arrivalStation;
    private String dateDeparture;
    //
    private String company_Name;
    private String departure_Time;
    private String arrival_Time;
    private String type_Seat;
    private int total_Seat;
    private int route_Time;
    private int route_Price;
    //
    private List<SeatDetails> list=new ArrayList<>();
    //
    private List<String> listBoarding=new ArrayList<>();
    private List<String>listDropping=new ArrayList<>();
    private String localBoarding;
    private String localDropping;
    private String coach_Route_Id;
    private String order_Id;
    //
    private List<Seat> seatList=new ArrayList<>();

    public BookingDetails(String strEmail, String strTel, String strName, String strPassword,
                          String departureStation, String arrivalStation, String dateDeparture,
                          String company_Name, String departure_Time, String arrival_Time,
                          String type_Seat, int total_Seat, int route_Time, int route_Price,
                          List<SeatDetails> list, List<String> listBoarding, List<String> listDropping,
                          String localBoarding, String localDropping, String coach_Route_Id,
                          String order_Id, List<Seat> seatList) {
        super(strEmail, strTel, strName, strPassword);
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.dateDeparture = dateDeparture;
        this.company_Name = company_Name;
        this.departure_Time = departure_Time;
        this.arrival_Time = arrival_Time;
        this.type_Seat = type_Seat;
        this.total_Seat = total_Seat;
        this.route_Time = route_Time;
        this.route_Price = route_Price;
        this.list = list;
        this.listBoarding = listBoarding;
        this.listDropping = listDropping;
        this.localBoarding = localBoarding;
        this.localDropping = localDropping;
        this.coach_Route_Id = coach_Route_Id;
        this.order_Id = order_Id;
        this.seatList = seatList;
    }

    public BookingDetails(String departureStation, String arrivalStation, String dateDeparture,
                          String company_Name, String departure_Time, String arrival_Time,
                          String type_Seat, int total_Seat, int route_Time, int route_Price,
                          List<SeatDetails> list, List<String> listBoarding, List<String> listDropping,
                          String localBoarding, String localDropping, String coach_Route_Id,
                          String order_Id, List<Seat> seatList) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.dateDeparture = dateDeparture;
        this.company_Name = company_Name;
        this.departure_Time = departure_Time;
        this.arrival_Time = arrival_Time;
        this.type_Seat = type_Seat;
        this.total_Seat = total_Seat;
        this.route_Time = route_Time;
        this.route_Price = route_Price;
        this.list = list;
        this.listBoarding = listBoarding;
        this.listDropping = listDropping;
        this.localBoarding = localBoarding;
        this.localDropping = localDropping;
        this.coach_Route_Id = coach_Route_Id;
        this.order_Id = order_Id;
        this.seatList = seatList;
    }

    public List<String> getListBoarding() {
        return listBoarding;
    }

    public void setListBoarding(List<String> listBoarding) {
        this.listBoarding = listBoarding;
    }

    public List<String> getListDropping() {
        return listDropping;
    }

    public void setListDropping(List<String> listDropping) {
        this.listDropping = listDropping;
    }

    public List<SeatDetails> getList() {
        return list;
    }

    public void setList(List<SeatDetails> list) {
        this.list=list;
    }

    public String getDeparture_Time() {
        return departure_Time;
    }

    public void setDeparture_Time(String departure_Time) {
        this.departure_Time = departure_Time;
    }

    public String getArrival_Time() {
        return arrival_Time;
    }

    public void setArrival_Time(String arrival_Time) {
        this.arrival_Time = arrival_Time;
    }

    public String getType_Seat() {
        return type_Seat;
    }

    public void setType_Seat(String type_Seat) {
        this.type_Seat = type_Seat;
    }

    public int getTotal_Seat() {
        return total_Seat;
    }

    public void setTotal_Seat(int total_Seat) {
        this.total_Seat = total_Seat;
    }

    public int getRoute_Time() {
        return route_Time;
    }

    public void setRoute_Time(int route_Time) {
        this.route_Time = route_Time;
    }

    public int getRoute_Price() {
        return route_Price;
    }

    public void setRoute_Price(int route_Price) {
        this.route_Price = route_Price;
    }

    //
    public BookingDetails() {
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

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public String getLocalBoarding() {
        return localBoarding;
    }

    public void setLocalBoarding(String localBoarding) {
        this.localBoarding = localBoarding;
    }

    public String getLocalDropping() {
        return localDropping;
    }

    public void setLocalDropping(String localDropping) {
        this.localDropping = localDropping;
    }

    public String getCoach_Route_Id() {
        return coach_Route_Id;
    }

    public void setCoach_Route_Id(String coach_Route_Id) {
        this.coach_Route_Id = coach_Route_Id;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
    }

    public String getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(String order_Id) {
        this.order_Id = order_Id;
    }
}
