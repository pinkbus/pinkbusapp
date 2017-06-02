package com.coachstationmanger.victor.pinkbus.models;

/**
 * Created by Victor on 28/04/2017.
 */

public class Company {
    private String companyName;
    private String companyId;
    private String companyTel;
    private byte[] companyPicture;
    private String companyAddress;

    public Company(String companyName, String companyId, String companyTel, byte[] companyPicture,
                   String companyAddress) {
        this.companyName = companyName;
        this.companyId = companyId;
        this.companyTel = companyTel;
        this.companyPicture = companyPicture;
        this.companyAddress = companyAddress;
    }

    public Company() {
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public byte[] getCompanyPicture() {
        return companyPicture;
    }

    public void setCompanyPicture(byte[] companyPicture) {
        this.companyPicture = companyPicture;
    }

    public String getCompanyName() {
        return companyName;
    }
}
