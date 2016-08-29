package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.Transaction;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionService {

    /**
     * Method to find all transaction entity objects available in database.
     *
     * @return The list of all available transaction entity object.
     */
    List<Transaction> findAllTransactions();

    /**
     * Method to find transaction entity object by its ID.
     *
     * @param transactionID The ID of transaction entity object to search with.
     * @return The transaction entity object matching provided ID if found, null
     * otherwise.
     */
    Transaction findTransactionByID(Long transactionID);

    /**
     * Method to find transaction entity object by its party ID.
     *
     * @param partyID The party ID of transaction entity object to search with.
     * @return The list of transaction entity object matching provided party ID.
     */
    List<Transaction> findTransactionsByParty(Long partyID);

    /**
     * Method to find transaction entity object by its transaction type.
     *
     * @param tranasctionType The transaction type ID of transaction entity
     * object to search with.
     * @return The list of transaction entity object matching provided
     * transaction type ID.
     */
    List<Transaction> findTransactionsByTransactionType(Long tranasctionType);

    /**
     * Method to find transaction entity object by its party ID and transaction
     * type.
     *
     * @param partyID The party ID of transaction entity object to search with.
     * @param transactionType The transaction type ID of transaction entity
     * object to search with.
     * @return The list of transaction entity object matching provided partyID
     * and transactionType search criteria.
     */
    List<Transaction> findTransactionsByPartyAndTransactionType(Long partyID, Long transactionType);

    /**
     * Method to persist transaction entity object to the database.
     *
     * @param transaction The transaction entity object to persist. The object
     * should not contain ID.
     * @return The persisted transaction entity object if operation went
     * successful, null otherwise.
     */
    Transaction saveTransaction(Transaction transaction);

    /**
     * Method to persist list of transaction entity objects to the database. It
     * makes use of saveTransaction() method to fulfill its operation.
     *
     * @param transactions The list of transaction entity objects to persist.
     * @return The last persisted transaction entity object if operation went
     * successful, null otherwise.
     */
    Transaction saveTransactions(List<Transaction> transactions);

    /**
     * Method to update existing transaction entity object with newly modified
     * transaction object.
     *
     * @param modifiedTransaction The modified transaction entity object to
     * update. The object should contain ID along with other necessary
     * attributes to successfully complete this operation.
     * @return The modified transaction entity object if operation went
     * successful, null otherwise.
     */
    Transaction updateTransaction(Transaction modifiedTransaction);

    /**
     * Method to update list of existing transaction entity objects with newly
     * modified transaction objects. The method makes use of updateTransaction()
     * method to fulfill its operation. It iterates through list of transaction
     * entity objects calling updateTransaction() method for each one of them.
     *
     * @param modifiedTransactions The list of modified transaction entity
     * objects to update.
     * @return The lastly updated transaction entity object if operation went
     * successful, null otherwise.
     */
    Transaction updateTransactions(List<Transaction> modifiedTransactions);
}
