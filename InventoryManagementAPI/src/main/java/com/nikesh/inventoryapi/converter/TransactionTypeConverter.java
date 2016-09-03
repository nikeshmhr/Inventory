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

    /**
     * Method that converts TransactionType entity object to
     * TransactionTypeResponseDTO object.
     *
     * @param transactionType The TransactionType entity object to convert.
     * @return Converted TransactionTypeResponseDTO object.
     * @throws Exception
     */
    public static TransactionTypeResponseDTO convertToResponseDTO(TransactionType transactionType) throws Exception {
        TransactionTypeResponseDTO response = new TransactionTypeResponseDTO();

        response.setId(transactionType.getId());
        response.setName(transactionType.getName());

        return response;
    }

    /**
     * Method that converts TransactionTypeRequestDTO object to TransactionType
     * entity object.
     *
     * @param request TransactionTypeRequestDTO object to convert.
     * @return Converted TransactionType entity object.
     * @throws Exception
     */
    public static TransactionType convetToEntity(TransactionTypeRequestDTO request) throws Exception {
        TransactionType transactionType = new TransactionType();

        transactionType.setId(request.getId());
        transactionType.setName(request.getName());

        return transactionType;
    }

    /**
     * Method that converts TransactionType entity object list to
     * TransactionTypeResponseDTO object list. The method makes use of
     * converToResponseDTO() method to convert every item from list.
     *
     * @param listOfTransactionType The TransactionType entity object list to
     * convert.
     * @return Converted TransactionTypeResponseDTO object lists.
     * @throws Exception
     */
    public static List<TransactionTypeResponseDTO> convertToResponseDTOList(List<TransactionType> listOfTransactionType) throws Exception {

        List<TransactionTypeResponseDTO> transactionTypeResponseDTOs = new ArrayList<>();

        for (TransactionType transactionType : listOfTransactionType) {
            transactionTypeResponseDTOs.add(convertToResponseDTO(transactionType));
        }

        return transactionTypeResponseDTOs;
    }

}
