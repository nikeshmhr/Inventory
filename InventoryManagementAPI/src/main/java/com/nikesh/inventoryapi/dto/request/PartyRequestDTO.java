package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nikesh Maharjan
 */
public class PartyRequestDTO implements Serializable {

    private String name;
    private String phoneNumber;
    private String email;
    private Double currentBalance;
    private Date balanceAsOfDate;
    private Character partyType;

    public PartyRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Date getBalanceAsOfDate() {
        return balanceAsOfDate;
    }

    public void setBalanceAsOfDate(Date balanceAsOfDate) {
        this.balanceAsOfDate = balanceAsOfDate;
    }

    public Character getPartyType() {
        return partyType;
    }

    public void setPartyType(Character partyType) {
        this.partyType = partyType;
    }
}
