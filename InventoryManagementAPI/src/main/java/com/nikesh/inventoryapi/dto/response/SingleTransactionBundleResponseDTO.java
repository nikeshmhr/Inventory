package com.nikesh.inventoryapi.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class SingleTransactionBundleResponseDTO implements Serializable {

    private TransactionResponseDTO transaction;
    private TransactionDetailResponseDTO transactionDetail;
    private List<TransactionItemResponseDTO> transactionItems;

    public SingleTransactionBundleResponseDTO() {

    }

    public TransactionResponseDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionResponseDTO transaction) {
        this.transaction = transaction;
    }

    public TransactionDetailResponseDTO getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(TransactionDetailResponseDTO transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public List<TransactionItemResponseDTO> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItemResponseDTO> transactionItems) {
        this.transactionItems = transactionItems;
    }

}
