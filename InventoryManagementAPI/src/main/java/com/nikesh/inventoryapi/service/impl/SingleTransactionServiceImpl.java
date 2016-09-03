package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.converter.TransactionConverter;
import com.nikesh.inventoryapi.converter.TransactionDetailConverter;
import com.nikesh.inventoryapi.converter.TransactionItemConverter;
import com.nikesh.inventoryapi.dto.response.SingleTransactionBundleResponseDTO;
import com.nikesh.inventoryapi.entity.Transaction;
import com.nikesh.inventoryapi.entity.TransactionDetail;
import com.nikesh.inventoryapi.entity.TransactionItem;
import com.nikesh.inventoryapi.repository.TransactionDetailRepository;
import com.nikesh.inventoryapi.repository.TransactionItemRepository;
import com.nikesh.inventoryapi.repository.TransactionRepository;
import com.nikesh.inventoryapi.service.SingleTransactionService;
import java.util.ArrayList;
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
public class SingleTransactionServiceImpl implements SingleTransactionService {

    @Autowired
    private TransactionRepository transRepo;

    @Autowired
    private TransactionItemRepository transItemRepo;

    @Autowired
    private TransactionDetailRepository transDetailRepo;

    @Override
    public Transaction addTransaction(Transaction transaction, TransactionDetail transactionDetail, List<TransactionItem> transactionItems) {

        Transaction savedTransaction = transRepo.save(transaction);

        transactionDetail.setTransaction(savedTransaction);

        TransactionDetail savedTransactionDetail = transDetailRepo.save(transactionDetail);

        for (TransactionItem transactionItem : transactionItems) {
            transactionItem.setTransactionDetail(savedTransactionDetail);
            transItemRepo.save(transactionItem);
        }

        return savedTransaction;
    }

    @Override
    public List<SingleTransactionBundleResponseDTO> findAllTransactions() throws Exception {
        List<SingleTransactionBundleResponseDTO> singleTransactionBundleResponseDTOs = new ArrayList<>();

        List<Transaction> transactions;

        List<TransactionItem> transactionItems;

        SingleTransactionBundleResponseDTO singleTransactionBundleResponseDTO;

        TransactionDetail transactionDetail;

        transactions = transRepo.findAll();

        for (Transaction transaction : transactions) {
            singleTransactionBundleResponseDTO = new SingleTransactionBundleResponseDTO();

            singleTransactionBundleResponseDTO.setTransaction(TransactionConverter.convertToResponseDTO(transaction));

            transactionDetail = transDetailRepo.findTransactionDetailByTransactionID(transaction.getId());

            singleTransactionBundleResponseDTO.setTransactionDetail(TransactionDetailConverter.convertToResponseDTO(transactionDetail));

            transactionItems = transItemRepo.findTransactionItemsByTransactionDetailId(transactionDetail.getId());

            singleTransactionBundleResponseDTO.setTransactionItems(TransactionItemConverter.convertToResponseDTOList(transactionItems));

            singleTransactionBundleResponseDTOs.add(singleTransactionBundleResponseDTO);

        }

        return singleTransactionBundleResponseDTOs;
    }

}
