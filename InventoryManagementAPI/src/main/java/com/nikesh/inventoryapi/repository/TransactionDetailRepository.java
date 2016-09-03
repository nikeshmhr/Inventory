package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

    @Query(value = "SELECT td FROM TransactionDetail td WHERE td.transaction.id=:transactionID")
    TransactionDetail findTransactionDetailByTransactionID(@Param("transactionID") Long transactionID);

    @Query(value = "SELECT td FROM TransactionDetail td WHERE td.billNo=:billNo")
    TransactionDetail findTransactionDetailByBillNo(@Param("billNo") String billNo);

}
