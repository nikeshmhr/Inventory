package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nikesh Maharjan
 */
public class UserRequestDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Date createdDate;
    private char status;
    private char userType;

    public UserRequestDTO() {
    }

    public UserRequestDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

}
