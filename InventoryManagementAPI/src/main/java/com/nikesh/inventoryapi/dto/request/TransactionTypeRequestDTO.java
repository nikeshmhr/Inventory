package com.nikesh.inventoryapi.dto.request;

import com.nikesh.inventoryapi.entity.Transaction;
import java.io.Serializable;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionTypeRequestDTO implements Serializable {

    private Long id;

    private String name;

    public TransactionTypeRequestDTO() {
    }

    public TransactionTypeRequestDTO(Long id) {
        this.id = id;
    }

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

}
