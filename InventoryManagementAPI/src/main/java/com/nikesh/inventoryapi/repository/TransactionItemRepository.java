package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.TransactionItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {

    @Query(name = "SELECT ti FROM TransactionItem ti WHERE ti.transactionDetail.id=:transactionDetailID")
    List<TransactionItem> findTransactionItemsByTransactionDetailId(@Param("transactionDetailID") Long transactionDetailID);

    @Query(name = "SELECT ti FROM TransactionItem ti WHERE ti.item.id=:itemID")
    List<TransactionItem> findTransactionItemsByItemId(Long itemID);

}
