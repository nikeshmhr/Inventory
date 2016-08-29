package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.TransactionDetail;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionDetailService {

    /**
     * Method that retrieves all the available transaction details.
     *
     * @return List of available transaction detail entity objects.
     */
    List<TransactionDetail> findAllTransactionDetails();

    /**
     * Method to find transaction detail by its ID.
     *
     * @param transactionDetailID The ID of transaction detail to search entity
     * from.
     * @return The transaction detail entity object that matches transaction
     * detail ID if found, null otherwise.
     */
    TransactionDetail findTransactionDetailByID(Long transactionDetailID);

    /**
     * Method to find transaction detail by its transaction ID.
     *
     * @param transactionID The transaction ID of the transaction detail entity
     * to search for.
     * @return The transaction detail entity object that matches the provided
     * transaction ID if found, null otherwise.
     */
    TransactionDetail findTransactionDetailByTransactionID(Long transactionID);

    /**
     * Method to find transaction detail by its bill no.
     *
     * @param billNo The bill no. of transaction detail entity to find by.
     * @return The transaction detail entity object matching provided bill no.
     * if found, null otherwise.
     */
    TransactionDetail findTransactionDetailByBillNo(String billNo);

    /**
     * Method to persist transaction detail entity object to database.
     *
     * @param transactionDetail The transaction detail entity object to persist.
     * The object should not contain ID.
     * @return The persisted transaction detail entity object if operation went
     * well, null otherwise.
     */
    TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail);

    /**
     * Method to persist list of transaction detail entity objects to database.
     * The method makes use of saveTransactionDetail() method to fulfill its
     * operation. The method iterates through list of transaction detail objects
     * calling saveTransactionDetail() for each one of them.
     *
     * @param transactionDetails The list of transaction detail entity objects
     * to persist.
     * @return The last persisted transaction detail entity object if everything
     * went well, null otherwise.
     */
    TransactionDetail saveTransactionDetails(List<TransactionDetail> transactionDetails);

    /**
     * Method to update the existing transaction detail entity object with
     * modified transaction detail.
     *
     * @param modifiedTransactionDetail The modified transaction detail entity
     * object to update. The object should contain its ID along with other
     * modified attributes to successfully complete this operation.
     * @return
     */
    TransactionDetail updateTransactionDetail(TransactionDetail modifiedTransactionDetail);

    /**
     * Method to update the list of existing transaction detail entity objects.
     * The method makes use of updateTransactionDetail() method to fulfill its
     * operation. It iterates through all transaction detail entity object from
     * list calling updateTransactionDetail() method on each one of them.
     *
     * @param modifiedTransactionDetails The list of modified transaction detail
     * entity objects to update.
     * @return The last updated transaction detail entity object if everything
     * went fine, null otherwise.
     */
    TransactionDetail updateTransactionDetails(List<TransactionDetail> modifiedTransactionDetails);

}
