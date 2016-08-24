package com.nikesh.inventoryapi.dto.response;

import java.io.Serializable;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionTypeResponseDTO implements Serializable {

    private Long id;

    private String name;

    public TransactionTypeResponseDTO() {
    }

    public TransactionTypeResponseDTO(Long id) {
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
