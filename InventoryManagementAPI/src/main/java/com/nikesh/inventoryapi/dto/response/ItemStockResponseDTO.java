package com.nikesh.inventoryapi.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nikesh Maharjan
 */
public class ItemStockResponseDTO implements Serializable {

    private Long id;

    private Long item;

    private Date createdDate;

    private Date lastModifiedDate;

    private Integer availableNoOfStocks;

    public ItemStockResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
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

    public Integer getAvailableNoOfStocks() {
        return availableNoOfStocks;
    }

    public void setAvailableNoOfStocks(Integer availableNoOfStocks) {
        this.availableNoOfStocks = availableNoOfStocks;
    }

}
