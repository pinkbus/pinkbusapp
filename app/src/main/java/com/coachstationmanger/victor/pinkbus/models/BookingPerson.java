package com.coachstationmanger.victor.pinkbus.models;

import java.io.Serializable;

/**
 * Created by Victor on 29/03/2017.
 */

public class BookingPerson implements Serializable{
    private String strEmail;
    private String strTel;
    private String strName;
    private String strPassword;
    private boolean loginStatus;

    public BookingPerson(String strEmail, String strTel, String strName, String strPassword) {
        this.strEmail = strEmail;
        this.strTel = strTel;
        this.strName = strName;
        this.strPassword = strPassword;
        this.loginStatus=false;
    }

    public BookingPerson() {
        this.loginStatus=false;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrTel() {
        return strTel;
    }

    public void setStrTel(String strTel) {
        this.strTel = strTel;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }
}
