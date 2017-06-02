package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 20/04/2017.
 */

public class TicketOrder extends TicketDetails implements Serializable {
    private int totalPrice;
    private String order_Date;
    private String status_Ticket;
    private String payout_Date;
    private String received_Date;
    private String payment_Id;
    private String order_Id;

    private List<Integer> listTicketId=new ArrayList<>();

    public TicketOrder() {
    }

    public TicketOrder(int totalPrice, String order_Date, String status_Ticket, String payout_Date,
                       String received_Date, String payment_Id, String order_Id, List<Integer> listTicketId) {
        this.totalPrice = totalPrice;
        this.order_Date = order_Date;
        this.status_Ticket = status_Ticket;
        this.payout_Date = payout_Date;
        this.received_Date = received_Date;
        this.payment_Id = payment_Id;
        this.order_Id = order_Id;
        this.listTicketId = listTicketId;
    }

    public TicketOrder(String email, Integer ticketID, String telOfPassenger, String nameOfPassenger,
                       String departureDate, String boardingLocal, String droppingLocal, String seatID,
                       String departureTime, String companyName, String departureStation,
                       String arrivalStation, int price, String coachRouteID, String orderID,
                       int totalPrice, String order_Date, String status_Ticket, String payout_Date,
                       String received_Date, String payment_Id, String order_Id, List<Integer> listTicketId) {
        super(email, ticketID, telOfPassenger, nameOfPassenger, departureDate, boardingLocal,
                droppingLocal, seatID, departureTime, companyName, departureStation, arrivalStation,
                price, coachRouteID, orderID);
        this.totalPrice = totalPrice;
        this.order_Date = order_Date;
        this.status_Ticket = status_Ticket;
        this.payout_Date = payout_Date;
        this.received_Date = received_Date;
        this.payment_Id = payment_Id;
        this.order_Id = order_Id;
        this.listTicketId = listTicketId;
    }

    public String getOrder_Date() {
        return order_Date;
    }

    public void setOrder_Date(String order_Date) {
        this.order_Date = order_Date;
    }

    public String getStatus_Ticket() {
        return status_Ticket;
    }

    public void setStatus_Ticket(String status_Ticket) {
        this.status_Ticket = status_Ticket;
    }

    public String getPayout_Date() {
        return payout_Date;
    }

    public void setPayout_Date(String payout_Date) {
        this.payout_Date = payout_Date;
    }

    public String getReceived_Date() {
        return received_Date;
    }

    public void setReceived_Date(String received_Date) {
        this.received_Date = received_Date;
    }

    public String getPayment_Id() {
        return payment_Id;
    }

    public void setPayment_Id(String payment_Id) {
        this.payment_Id = payment_Id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(String order_Id) {
        this.order_Id = order_Id;
    }

    public List<Integer> getListTicketId() {
        return listTicketId;
    }

    public void setListTicketId(List<Integer> listTicketId) {
        this.listTicketId = listTicketId;
    }
}
