package com.nikesh.inventoryapi.controller;

import com.nikesh.inventoryapi.converter.TransactionTypeConverter;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.TransactionTypeResponseDTO;
import com.nikesh.inventoryapi.entity.TransactionType;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.TransactionTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nikesh Maharjan
 */
@RestController
@RequestMapping("/api/transaction-types")
public class TransactionTypeController {

    @Autowired
    private TransactionTypeService transactionTypeService;

    private List<TransactionType> listOfTransactionType;

    private TransactionType transactionType;

    private List<TransactionTypeResponseDTO> transactionTypeResponseDTOs;

    private TransactionTypeResponseDTO transactionTypeResponseDTO;

    @RequestMapping(method = GET)
    public ResponseEntity<List<TransactionTypeResponseDTO>> getAll() {

        // RETURNS THE LIST OF ALL AVAILABLE TransactionType
        listOfTransactionType = transactionTypeService.findAllTransactionTypes();

        // CHECK IF LIST IS NULL OR EMPTY
        if (listOfTransactionType == null || listOfTransactionType.isEmpty()) {
            // THROW ErrorResponse
            throw new GenericException(new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Transaction types not found.",
                    "Transaction types not found. Transaction type list returned empty.")
            );
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT INTO TransactionTypeResponseDTO LIST
                transactionTypeResponseDTOs = TransactionTypeConverter.convertToResponseDTOList(listOfTransactionType);

                // RETURN RESPONSE WITH OK (200) STATUS
                return new ResponseEntity<>(transactionTypeResponseDTOs, HttpStatus.OK);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not read transactio type list because of failed conversion.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    @RequestMapping(value = "/{transId}", method = GET)
    public ResponseEntity<TransactionTypeResponseDTO> getOneTransactionType(@PathVariable("transId") Long transId) {
        // CHECK IF id IS INVALID
        if (transId == null || transId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request, invalid transaction type id. [" + transId + "]."
            ));
        } else {
            // RETURNS EXISTING TransactionType
            transactionType = transactionTypeService.findTransactionTypeById(transId);

            // CHECK IF TransactionType IS NULL
            if (transactionType == null) {
                // THROW ErrorResponse
                throw new GenericException(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND,
                        "Transaction Type not found.",
                        "Transaction Type not found. TransactionType returned empty (null).")
                );
            } else {
                // TRY AND CATCH FOR ANY EXCEPTION
                try {
                    // CONVERT INTO TransactionTypeResponseDTO
                    transactionTypeResponseDTO = TransactionTypeConverter.convertToResponseDTO(transactionType);

                    // RETURN RESPONSE WITH OK (200) STATUS
                    return new ResponseEntity<>(transactionTypeResponseDTO, HttpStatus.OK);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new GenericException(new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Could not read transaction type because of failed conversion.",
                            "Conversion failed. Exception: [" + ex.getMessage() + "]"
                    ));
                }
            }
        }
    }

}
