package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.dto.request.TransactionSearchRequestParamDto;
import com.nikesh.inventoryapi.dto.response.SingleTransactionBundleResponseDTO;
import com.nikesh.inventoryapi.entity.Transaction;
import com.nikesh.inventoryapi.entity.TransactionDetail;
import com.nikesh.inventoryapi.entity.TransactionItem;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface SingleTransactionService {

    /**
     * Service that enables to add the transaction to the system.
     *
     * @param transaction The transaction entity object containing basic common
     * attribute of a transaction.
     * @param transactionDetail The transaction detail entity object containing
     * detail about transaction.
     * @param transactionItems The list of transaction items involved in this
     * particular transaction including quantity and amount.
     * @return The transaction that is added if successful, null otherwise.
     */
    Transaction addTransaction(Transaction transaction, TransactionDetail transactionDetail, List<TransactionItem> transactionItems);

    /**
     * Service that finds and fetches all available transactions from the system
     *
     * @return The list of SingleTransactionBundleResponseDTO object containing
     * TransactionResponseDTO, TransactionDetailResponseDTO and
     * TransactionItemResponseDTO list which combined represents a single
     * transaction.
     * @throws Exception
     */
    List<SingleTransactionBundleResponseDTO> findAllTransactions() throws Exception;

    List<SingleTransactionBundleResponseDTO> searchTxnToExport(TransactionSearchRequestParamDto searchParam);
}
