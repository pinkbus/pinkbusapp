package com.coachstationmanger.victor.pinkbus.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 24/03/2017.
 */

public class RouteDetails {
    private String company_Name;
    private String departure_Time;
    private String arrival_Time;
    private String type_Seat;
    private int total_Seat;
    private int route_Time;
    private int route_Price;
    private List<String> listBoarding=new ArrayList<>();
    private List<String> listDropping=new ArrayList<>();
    private List<Seat> seatList=new ArrayList<>();
    private String coach_Route_Id;

    public RouteDetails() {
    }

    public RouteDetails(String company_Name, String departure_Time, String arrival_Time,
                        String type_Seat, int total_Seat, int route_Time, int route_Price,
                        List<String> listBoarding, List<String> listDropping, List<Seat> seatList, String coach_Route_Id) {
        this.company_Name = company_Name;
        this.departure_Time = departure_Time;
        this.arrival_Time = arrival_Time;
        this.type_Seat = type_Seat;
        this.total_Seat = total_Seat;
        this.route_Time = route_Time;
        this.route_Price = route_Price;
        this.listBoarding = listBoarding;
        this.listDropping = listDropping;
        this.seatList = seatList;
        this.coach_Route_Id = coach_Route_Id;
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

    public String getType_Seat() {
        return type_Seat;
    }

    public void setType_Seat(String type_Seat) {
        this.type_Seat = type_Seat;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
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

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public String getCoach_Route_Id() {
        return coach_Route_Id;
    }

    public void setCoach_Route_Id(String coach_Route_Id) {
        this.coach_Route_Id = coach_Route_Id;
    }
}
