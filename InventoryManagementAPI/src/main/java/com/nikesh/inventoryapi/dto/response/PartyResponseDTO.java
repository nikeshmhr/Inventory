package com.nikesh.inventoryapi.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nikesh Maharjan
 */
public class PartyResponseDTO implements Serializable {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private Double currentBalance;
    private Date balanceAsOfDate;
    private Character partyType;
    private Date createdDate;
    private Date lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
