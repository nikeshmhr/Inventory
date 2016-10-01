package com.nikesh.inventoryapi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nikesh Maharjan
 */
@Entity
@Table(name = "item_stock_info")
public class ItemStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "last_modified_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date lastModifiedDate;

    @Column(name = "available_no_stocks", nullable = false)
    private Integer availableNoOfStocks;

    public ItemStock() {
    }

    public ItemStock(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
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
