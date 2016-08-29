package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(name = "SELECT * FROM Transaction t WHERE t.party.id=:partyID")
    List<Transaction> findTransactionsByParty(@Param("partyID") Long partyID);

    @Query(name = "SELECT * FROM Transaction t WHERE t.transactionType.id=:transactionType")
    List<Transaction> findTransactionsByTransactionType(@Param("transactionType") Long tranasctionType);

    @Query(name = "SELECT * FROM Transaction t WHERE t.party.id=:partyID AND t.transactionType.id=:transactionType")
    List<Transaction> findTransactionsByPartyAndTransactionType(@Param("partyID") Long partyID, @Param("transactionType") Long transactionType);

}
