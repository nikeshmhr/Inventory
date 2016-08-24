package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.TransactionTypeRequestDTO;
import com.nikesh.inventoryapi.dto.response.TransactionTypeResponseDTO;
import com.nikesh.inventoryapi.entity.TransactionType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionTypeConverter {

    public static TransactionTypeResponseDTO convertToResponseDTO(TransactionType transactionType) {
        TransactionTypeResponseDTO response = new TransactionTypeResponseDTO();

        response.setId(transactionType.getId());
        response.setName(transactionType.getName());

        return response;
    }

    public static TransactionType convetToEntity(TransactionTypeRequestDTO request) {
        TransactionType transactionType = new TransactionType();

        transactionType.setId(request.getId());
        transactionType.setName(request.getName());

        return transactionType;
    }

    public static List<TransactionTypeResponseDTO> convertToResponseDTOList(List<TransactionType> listOfTransactionType) {

        List<TransactionTypeResponseDTO> transactionTypeResponseDTOs = new ArrayList<>();

        for (TransactionType transactionType : listOfTransactionType) {
            transactionTypeResponseDTOs.add(convertToResponseDTO(transactionType));
        }

        return transactionTypeResponseDTOs;
    }

}
