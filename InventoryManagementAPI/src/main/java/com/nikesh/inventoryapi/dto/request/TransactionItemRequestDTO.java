package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionItemRequestDTO implements Serializable {

    private Long id;

    private Long item;

    private Long transactionDetail;

    private Integer quantity;

    private Double amount;

    public TransactionItemRequestDTO() {

    }

    public TransactionItemRequestDTO(Long id) {
        this.id = id;
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

    public Long getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(Long transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
