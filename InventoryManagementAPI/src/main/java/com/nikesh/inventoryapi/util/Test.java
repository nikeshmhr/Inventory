package com.nikesh.inventoryapi.util;

import com.google.gson.Gson;
import com.nikesh.inventoryapi.dto.request.SingleTransactionBundleRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionDetailRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionItemRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionRequestDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class Test {

    public static void main(String[] args) {
        Gson gson = new Gson();
        SingleTransactionBundleRequestDTO transaction = new SingleTransactionBundleRequestDTO();

        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(1L);
        transactionRequest.setParty(1L);
        transactionRequest.setTotalAmount(10000.00);
        transactionRequest.setTransactionDate(new Date());
        transactionRequest.setTransactionType(1L);
        transaction.setTransaction(transactionRequest);

        TransactionDetailRequestDTO transactionDetail = new TransactionDetailRequestDTO(1L);
        transactionDetail.setBalance(1000.00);
        transactionDetail.setBillNo("B101");
        transactionDetail.setDiscountAmount(100.00);
        transactionDetail.setDiscountPercent(10.00);
        transactionDetail.setPaidAmount(100.00);
        transactionDetail.setSubTotal(900.00);
        transactionDetail.setTaxAmount(10.00);
        transactionDetail.setTaxPercent(10.00);
        transactionDetail.setTransaction(1L);
        transaction.setTransactionDetail(transactionDetail);

        List<TransactionItemRequestDTO> transactionItems = new ArrayList<>();
        TransactionItemRequestDTO transactionItemRequestDTO = new TransactionItemRequestDTO(1L);
        transactionItemRequestDTO.setQuantity(10);
        transactionItemRequestDTO.setAmount(1000.00);
        transactionItemRequestDTO.setItem(1L);
        transactionItemRequestDTO.setTransactionDetail(1L);

        transactionItems.add(transactionItemRequestDTO);

        transactionItemRequestDTO = new TransactionItemRequestDTO(1L);
        transactionItemRequestDTO.setQuantity(10);
        transactionItemRequestDTO.setAmount(1000.00);
        transactionItemRequestDTO.setItem(1L);
        transactionItemRequestDTO.setTransactionDetail(1L);

        transactionItems.add(transactionItemRequestDTO);

        transaction.setTransactionItems(transactionItems);

        String toJson = gson.toJson(transaction);
        System.out.println(toJson);
    }

}
