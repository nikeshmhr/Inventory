package com.nikesh.inventoryapi.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nikesh Maharjan
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "createdDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "status", nullable = false)
    private char status;

    @Column(name = "user_type", nullable = false)
    private char userType;

    public User() {

    }

    public User(Long id) {
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
