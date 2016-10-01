package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.constants.SystemConstants;
import com.nikesh.inventoryapi.converter.TransactionConverter;
import com.nikesh.inventoryapi.converter.TransactionDetailConverter;
import com.nikesh.inventoryapi.converter.TransactionItemConverter;
import com.nikesh.inventoryapi.dto.response.SingleTransactionBundleResponseDTO;
import com.nikesh.inventoryapi.entity.ItemStock;
import com.nikesh.inventoryapi.entity.Party;
import com.nikesh.inventoryapi.entity.Transaction;
import com.nikesh.inventoryapi.entity.TransactionDetail;
import com.nikesh.inventoryapi.entity.TransactionItem;
import com.nikesh.inventoryapi.entity.TransactionType;
import com.nikesh.inventoryapi.repository.ItemStockRepository;
import com.nikesh.inventoryapi.repository.PartyRepository;
import com.nikesh.inventoryapi.repository.TransactionDetailRepository;
import com.nikesh.inventoryapi.repository.TransactionItemRepository;
import com.nikesh.inventoryapi.repository.TransactionRepository;
import com.nikesh.inventoryapi.service.SingleTransactionService;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ItemStockRepository itemStockRepo;

    @Autowired
    private PartyRepository partyRepo;

    @Override
    public Transaction addTransaction(Transaction transaction, TransactionDetail transactionDetail, List<TransactionItem> transactionItems) {

        Transaction savedTransaction = transRepo.save(transaction);

        TransactionType type = transaction.getTransactionType();

        Date currentDate = new Date();

        Party party = transaction.getParty();

        party.setBalanceAsOfDate(currentDate);
        party.setLastModifiedDate(currentDate);

        // CHECK FOR TRANSACTION TYPE
        if (type.getId() == SystemConstants.TransactionTypeConstants.CASH_IN) {
            Double currentBalance = party.getCurrentBalance();
            Double cashInAmount = transactionDetail.getPaidAmount();

            party.setCurrentBalance(currentBalance - cashInAmount);
            partyRepo.save(party);

            transactionDetail.setTransaction(savedTransaction);
            transactionDetail.setBalance(0D);

            TransactionDetail savedTransactionDetail = transDetailRepo.save(transactionDetail);
        } else if (type.getId() == SystemConstants.TransactionTypeConstants.CASH_OUT) {
            Double currentBalance = party.getCurrentBalance();
            Double cashOutAmount = transactionDetail.getPaidAmount();

            party.setCurrentBalance(currentBalance - cashOutAmount);
            partyRepo.save(party);

            transactionDetail.setTransaction(savedTransaction);
            transactionDetail.setBalance(0D);

            TransactionDetail savedTransactionDetail = transDetailRepo.save(transactionDetail);
        } else {    // FOR TRANSACTION TYPE PURCHASE AND SALE
            transactionDetail.setTransaction(savedTransaction);

            TransactionDetail savedTransactionDetail = transDetailRepo.save(transactionDetail);

            String transactionType = transaction.getTransactionType().getName().toLowerCase().trim();

            for (TransactionItem transactionItem : transactionItems) {
                transactionItem.setTransactionDetail(savedTransactionDetail);
                transItemRepo.save(transactionItem);

                // ALSO DEDUCT OR ADD RESPECTIVE ITEMS FROM THE STOCK
                if (transactionType.contains("purchase")) {   // ADD TO STOCK
                    ItemStock itemStock = itemStockRepo.findStockByItemId(transactionItem.getItem().getId());

                    if (itemStock == null) {  // MEANS NEW ITEM OR NO EXISTING STOCK FOR THIS ITEMS
                        // ADD TO STOCK.
                        itemStock = new ItemStock();
                        itemStock.setAvailableNoOfStocks(transactionItem.getQuantity());
                        itemStock.setCreatedDate(new Date());
                        itemStock.setItem(transactionItem.getItem());
                        itemStock.setLastModifiedDate(new Date());

                        itemStockRepo.save(itemStock);
                    } else {    // UPDATE EXISTING.
                        Integer availableNoOfStocks = itemStock.getAvailableNoOfStocks();
                        itemStock.setAvailableNoOfStocks(availableNoOfStocks + transactionItem.getQuantity());
                        itemStock.setLastModifiedDate(new Date());

                        itemStockRepo.save(itemStock);
                    }
                } else if (transactionType.contains("sale")) {  // DEDUCT FROM STOCK
                    ItemStock itemStock = itemStockRepo.findStockByItemId(transactionItem.getItem().getId());
                    Integer availableNoOfStocks = itemStock.getAvailableNoOfStocks();
                    itemStock.setAvailableNoOfStocks(availableNoOfStocks - transactionItem.getQuantity());

                    itemStockRepo.save(itemStock);
                }
            }

            // DEDUG OR ADD REMAINING BALANCE TO RESPECTIVE A/C
            Double remainingBalance = transactionDetail.getBalance();
            Double currentBalance = party.getCurrentBalance();

            party.setCurrentBalance(currentBalance + remainingBalance);
            partyRepo.save(party);
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
