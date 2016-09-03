package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.TransactionRequestDTO;
import com.nikesh.inventoryapi.dto.response.TransactionResponseDTO;
import com.nikesh.inventoryapi.entity.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionConverter {

    /**
     * Method that converts TransactionRequestDTO object to its corresponding
     * Transaction entity object. This method is called for conversion before
     * persisting request into database.
     *
     * @param transactionRequestDTO Object to convert to entity.
     * @return Converted Transaction entity object.
     * @throws Exception
     */
    public static Transaction convertToEntity(TransactionRequestDTO transactionRequestDTO) throws Exception {
        Transaction transaction = new Transaction();

        transaction.setId(transactionRequestDTO.getId());
        transaction.setTotalAmount(transactionRequestDTO.getTotalAmount());
        transaction.setTransactionDate(transactionRequestDTO.getTransactionDate());

        return transaction;
    }

    /**
     * Method that converts Transaction entity object to its corresponding
     * TransactionResponseDTO object. This method is used for converting entity
     * object before sending response to the user.
     *
     * @param transaction Entity object to convert to response DTO.
     * @return Converted TransactionResponseDTO object.
     * @throws Exception
     */
    public static TransactionResponseDTO convertToResponseDTO(Transaction transaction) throws Exception {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        transactionResponseDTO.setId(transaction.getId());
        transactionResponseDTO.setParty(transaction.getParty().getId());
        transactionResponseDTO.setTotalAmount(transaction.getTotalAmount());
        transactionResponseDTO.setTransactionDate(transaction.getTransactionDate());
        transactionResponseDTO.setTransactionType(transaction.getTransactionType().getId());

        return transactionResponseDTO;
    }

    /**
     * Method that converts Transaction entity object list to
     * TransactionResponseDTO list. This method makes use of
     * convertToResponseDTO() method to convert all objects of the list.
     *
     * @param transactions List of Transaction entity objects to convert.
     * @return Converted TransactionResponseDTO list.
     * @throws Exception
     */
    public static List<TransactionResponseDTO> convertToResponseDTOList(List<Transaction> transactions) throws Exception {
        List<TransactionResponseDTO> transactionResponseDTOs = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionResponseDTOs.add(convertToResponseDTO(transaction));
        }

        return transactionResponseDTOs;
    }

}
