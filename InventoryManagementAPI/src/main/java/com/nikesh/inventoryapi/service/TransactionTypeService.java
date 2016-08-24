package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.TransactionType;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionTypeService {

    /**
     * Method to find the list of all available TransctionType Entities.
     *
     * @return List of TransactionType entity.
     */
    List<TransactionType> findAllTransactionTypes();

    /**
     * Method to find the TransactionType entity by its id.
     *
     * @param id The id of the TransactionType to search for.
     * @return The TransactionType entity if found null otherwise.
     */
    TransactionType findTransactionTypeById(Long id);

    /**
     * Method to find the TransactionType entity by its name.
     *
     * @param name The name of TransactionType to search for.
     * @return The TransactionType entity if found null otherwise.
     */
    TransactionType findTransactionTypeByName(String name);

}
