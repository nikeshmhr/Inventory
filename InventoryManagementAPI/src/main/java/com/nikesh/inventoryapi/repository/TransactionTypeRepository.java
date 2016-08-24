package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findByName(String name);

}
