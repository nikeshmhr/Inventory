package com.nikesh.inventoryapi.controller;

import com.google.gson.Gson;
import com.nikesh.inventoryapi.converter.TransactionConverter;
import com.nikesh.inventoryapi.converter.TransactionDetailConverter;
import com.nikesh.inventoryapi.converter.TransactionItemConverter;
import com.nikesh.inventoryapi.dto.request.SingleTransactionBundleRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionItemRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionRequestDTO;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.SingleTransactionBundleResponseDTO;
import com.nikesh.inventoryapi.dto.response.TransactionResponseDTO;
import com.nikesh.inventoryapi.entity.Transaction;
import com.nikesh.inventoryapi.entity.TransactionDetail;
import com.nikesh.inventoryapi.entity.TransactionItem;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.ItemService;
import com.nikesh.inventoryapi.service.PartyService;
import com.nikesh.inventoryapi.service.SingleTransactionService;
import com.nikesh.inventoryapi.service.TransactionTypeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nikesh Maharjan
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private SingleTransactionService singleTransactionService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private ItemService itemService;

    private TransactionRequestDTO transactionRequestDTO;

    private List<TransactionItemRequestDTO> transactionItemRequestDTOs;

    private TransactionItem transactionItem;

    private List<TransactionItem> transactionItems;

    private TransactionDetail transactionDetail;

    private Transaction transaction;

    private TransactionResponseDTO transactionResponseDTO;

    /**
     * Resource URI to get all existing transactions.
     *
     * @return List of SingleTransactionBundleResponseDTO containing details of
     * transaction.
     */
    @RequestMapping(method = GET)
    public ResponseEntity<List<SingleTransactionBundleResponseDTO>> getAllTransactions() {
        try {
            List<SingleTransactionBundleResponseDTO> allTransactions = singleTransactionService.findAllTransactions();

            Gson gson = new Gson();

            if (allTransactions.isEmpty()) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND,
                                "Transaction not found.",
                                "Transaction list returned empty."
                        )
                );
            } else {
                System.out.println("JSON: " + gson.toJson(allTransactions.get(0)));

                return new ResponseEntity<>(allTransactions, HttpStatus.OK);
            }
        } catch (GenericException ge) {
            throw ge;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Something went wrong while retrieving transactions.",
                            "Expectation failed may be due to failed conversion."
                    )
            );
        }
    }

    /**
     * Resource URI to create/post/add new transaction.
     *
     * @param transReq The SingleTransactionBundleRequestDTO object containing
     * the details of transaction.
     * @return TransactionResponseDTO object containing basic transaction
     * information of created transaction.
     */
    @RequestMapping(method = POST)
    public ResponseEntity<TransactionResponseDTO> addTransaction(
            @RequestBody SingleTransactionBundleRequestDTO transReq) {
        try {
            // EXTRACT TransactionRequestDTO OBJECT.
            transactionRequestDTO = transReq.getTransaction();

            // CONVERT IT INTO Transaction ENTITY OBJECT
            // SET PARTY
            // SET TRANSACTION TYPE
            transaction = TransactionConverter.convertToEntity(transactionRequestDTO);
            transaction.setParty(partyService.findPartyById(transactionRequestDTO.getParty()));
            transaction.setTransactionType(transactionTypeService.findTransactionTypeById(transactionRequestDTO.getTransactionType()));

            // CONVERT TransactionDetailRequestDTO TO TransactionDetail ENTITY OBJECT.
            transactionDetail = TransactionDetailConverter.convertToEntity(transReq.getTransactionDetail());

            // EXTRACT TransactionItemRequestDTO LIST.
            // INITIALIZE TransactionItem ENTITY LIST.
            transactionItemRequestDTOs = transReq.getTransactionItems();
            transactionItems = new ArrayList<>();

            // ITERATE THROUGH EVERY TransactionItemRequestDTO OBJECT
            for (TransactionItemRequestDTO reqDTO : transactionItemRequestDTOs) {
                // CONVERT CURRENT TransactionItemRequestDTO OBJECT INTO TransactionItem ENTITY OBJECT
                // SET ITEM PROPERTY
                transactionItem = TransactionItemConverter.convertToEntity(reqDTO);
                transactionItem.setItem(itemService.findItemById(reqDTO.getItem()));

                // ADD CONVERTED TransactionItem ENTITY OBJECT TO TransactionItem ENTITY OBJECT LIST
                transactionItems.add(transactionItem);
            }

            // PERSIST TRANSACTION AND EXPECT Transaction ENTITY OBJECT.
            Transaction savedTransaction = singleTransactionService.addTransaction(transaction, transactionDetail, transactionItems);

            // CHECK IF savedTransaction OBJECT IS NULL OR IF ITS ID IS NULL
            // IF SO THEN THROW EXCEPTION.
            if (savedTransaction == null || savedTransaction.getId() == null) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.EXPECTATION_FAILED.value(),
                                HttpStatus.EXPECTATION_FAILED,
                                "Failed to add transaction.",
                                "Failed to add transaction. The persisted transaction object was either null or its ID was null."
                        )
                );
            } else {    // OTHERWISE
                // CONVERT PERSISTED Transaction ENTITY OBJECT TO TransactionResponseDTO OBJECT
                transactionResponseDTO = TransactionConverter.convertToResponseDTO(savedTransaction);

                return new ResponseEntity<>(transactionResponseDTO, HttpStatus.CREATED);
            }

        } catch (GenericException gex) {
            throw gex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Something went wrong while persisting or retriving persisted transactions.",
                            "Expectation failed may be due to failed conversion or save."
                    )
            );
        }
    }

}
