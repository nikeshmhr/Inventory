package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;

/**
 *
 * @author Nikesh Maharjan
 */
public class ItemStockRequestDTO implements Serializable {

    private Long id;

    private Long item;

    private Integer availableNoOfStocks;

    public ItemStockRequestDTO() {
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

    public Integer getAvailableNoOfStocks() {
        return availableNoOfStocks;
    }

    public void setAvailableNoOfStocks(Integer availableNoOfStocks) {
        this.availableNoOfStocks = availableNoOfStocks;
    }

}
