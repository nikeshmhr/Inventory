package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.entity.Transaction;
import com.nikesh.inventoryapi.repository.TransactionRepository;
import com.nikesh.inventoryapi.service.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nikesh Maharjan
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findTransactionByID(Long transactionID) {
        return transactionRepository.findOne(transactionID);
    }

    @Override
    public List<Transaction> findTransactionsByParty(Long partyID) {
        return transactionRepository.findTransactionsByParty(partyID);
    }

    @Override
    public List<Transaction> findTransactionsByTransactionType(Long tranasctionType) {
        return transactionRepository.findTransactionsByTransactionType(tranasctionType);
    }

    @Override
    public List<Transaction> findTransactionsByPartyAndTransactionType(Long partyID, Long transactionType) {
        return transactionRepository.findTransactionsByPartyAndTransactionType(partyID, transactionType);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction saveTransactions(List<Transaction> transactions) {
        Transaction transaction = null;

        for (int i = 0; i < transactions.size(); i++) {
            if ((i + 1) == transactions.size()) {
                transaction = saveTransaction(transactions.get(i));
            } else {
                saveTransaction(transactions.get(i));
            }
        }

        return transaction;
    }

    @Override
    public Transaction updateTransaction(Transaction modifiedTransaction) {
        if (modifiedTransaction.getId() == null) {
            return null;
        } else {
            return transactionRepository.save(modifiedTransaction);
        }
    }

    @Override
    public Transaction updateTransactions(List<Transaction> modifiedTransactions) {
        Transaction transaction = null;

        for (int i = 0; i < modifiedTransactions.size(); i++) {
            if ((i + 1) == modifiedTransactions.size()) {
                transaction = updateTransaction(modifiedTransactions.get(i));
            } else {
                updateTransaction(modifiedTransactions.get(i));
            }
        }

        return transaction;
    }

}
