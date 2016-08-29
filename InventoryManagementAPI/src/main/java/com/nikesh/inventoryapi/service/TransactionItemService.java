package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.TransactionItem;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface TransactionItemService {

    /**
     * Method that retrieves all the transaction items available.
     *
     * @return List of database TransactionItem entity object.
     */
    List<TransactionItem> findAllTransactionItems();

    /**
     * Method that retrieves a transaction item by its ID.
     *
     * @param id The ID of transaction item to find and retrieve.
     * @return The TransactionItem object with provided ID if found, null
     * otherwise.
     */
    TransactionItem findTransactionItemByID(Long id);

    /**
     * Method that retrieves all the transaction items by its transaction detail
     * ID.
     *
     * @param transactionDetailID The ID of transaction detail to find
     * transaction items by.
     * @return List of TransactionItem object matching the provided transaction
     * detail ID.
     */
    List<TransactionItem> findTransactionItemsByTransactionDetailID(Long transactionDetailID);

    /**
     * Method that retrieves all the transaction items by its item ID.
     *
     * @param itemID The item ID to find transaction items by.
     * @return List of TransactionItem object matching the provided item ID.
     */
    List<TransactionItem> findTransactionItemsByItemID(Long itemID);

    /**
     * Method that persists the transaction item into the database.
     *
     * @param transactionItem The transaction item entity object to persist.
     * @return The persisted transaction item entity object if successful
     * otherwise null.
     */
    TransactionItem saveTransactionItem(TransactionItem transactionItem);

    /**
     * Method that persists the transaction items into the database. This method
     * makes use of saveTransactionIem() method to fulfill its request. The
     * method iterates through all the transaction item objects in the list
     * calling saveTransactionItem() method for each one of them.
     *
     * @param transactionItems The list of transaction item entity objects to
     * persist.
     * @return The last persisted transaction item entity object if everything
     * went well otherwise null.
     */
    TransactionItem saveTransactionItems(List<TransactionItem> transactionItems);

    /**
     * Method that updates the transaction item using the newly modified
     * transaction item entity object.
     *
     * @param modifiedTransactionItem The modified transaction item entity
     * object to update. The object should have its ID set in order to
     * successfully complete this operation.
     * @return The updated object if update went find, null otherwise.
     */
    TransactionItem updateTransactionItem(TransactionItem modifiedTransactionItem);

    /**
     * Method that updates list of transaction items using newly modified
     * transaction item entity objects. This method makes use of
     * updateTransactionItem() in order to complete its process. The method
     * iterates through all the object in the list calling updateTransaction()
     * method on every object.
     *
     * @param modifiedTransactionItems
     * @return The last updated item if everything went well, null otherwise.
     */
    TransactionItem updateTransactionItems(List<TransactionItem> modifiedTransactionItems);

}
