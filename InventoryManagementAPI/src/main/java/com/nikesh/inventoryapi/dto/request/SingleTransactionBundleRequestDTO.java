package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that bundles transaction data together. This class is used when
 * creating new transaction request.
 *
 * @author Nikesh Maharjan
 */
public class SingleTransactionBundleRequestDTO implements Serializable {

    private TransactionRequestDTO transaction;
    private TransactionDetailRequestDTO transactionDetail;
    private List<TransactionItemRequestDTO> transactionItems;

    public SingleTransactionBundleRequestDTO() {

    }

    public TransactionRequestDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionRequestDTO transaction) {
        this.transaction = transaction;
    }

    public TransactionDetailRequestDTO getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(TransactionDetailRequestDTO transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public List<TransactionItemRequestDTO> getTransactionItems() {
        if (this.transactionItems == null) {
            this.transactionItems = new ArrayList<>();
        }
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItemRequestDTO> transactionItems) {
        this.transactionItems = transactionItems;
    }

}
