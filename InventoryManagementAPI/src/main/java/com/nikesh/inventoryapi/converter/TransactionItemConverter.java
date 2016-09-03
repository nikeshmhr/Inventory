package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.TransactionItemRequestDTO;
import com.nikesh.inventoryapi.dto.response.TransactionItemResponseDTO;
import com.nikesh.inventoryapi.entity.TransactionItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionItemConverter {

    /**
     * Method that converts TransactionItemRequestDTO object to TransactionItem.
     *
     * @param req The TransactionItemRequestDTO object to convert.
     * @return Converted TransactionItem entity object.
     * @throws Exception
     */
    public static TransactionItem convertToEntity(TransactionItemRequestDTO req) throws Exception {
        TransactionItem ti = new TransactionItem();

        ti.setAmount(req.getAmount());
        ti.setId(req.getId());
        ti.setQuantity(req.getQuantity());

        return ti;
    }

    /**
     * Method that converts TransactionItemRequestDTO object list to
     * TransactionItem entity object list. The method makes use of
     * convertToEntity() method to convert every item on its list.
     *
     * @param requestList The TransactionItemRequestDTO list to convert.
     * @return Converted TransactionItem entity object list.
     * @throws Exception
     */
    public static List<TransactionItem> convertToEntityList(List<TransactionItemRequestDTO> requestList) throws Exception {
        List<TransactionItem> responseLists = new ArrayList<>();

        for (TransactionItemRequestDTO request : requestList) {
            responseLists.add(convertToEntity(request));
        }

        return responseLists;
    }

    /**
     * Method that converts TransactionItem entity object to
     * TransactionItemResponseDTO.
     *
     * @param ti The TransactionItem entity object to convert.
     * @return Converted TransactionItemResponseDTO object.
     * @throws Exception
     */
    public static TransactionItemResponseDTO convertToResponseDTO(TransactionItem ti) throws Exception {
        TransactionItemResponseDTO response = new TransactionItemResponseDTO();

        response.setAmount(ti.getAmount());
        response.setId(ti.getId());
        response.setItem(ti.getItem().getId());
        response.setQuantity(ti.getQuantity());
        response.setTransactionDetail(ti.getTransactionDetail().getId());

        return response;
    }

    /**
     * Method to convert TransactionItem entity object list to
     * TransactionItemResponseDTO list. The method makes use of
     * convertToResponseDTO() to convert every item from list.
     *
     * @param tis The TransactionItem list to convert.
     * @return Converted TransactionItemResponseDTO list.
     * @throws Exception
     */
    public static List<TransactionItemResponseDTO> convertToResponseDTOList(List<TransactionItem> tis) throws Exception {
        List<TransactionItemResponseDTO> responseList = new ArrayList<>();

        for (TransactionItem ti : tis) {
            responseList.add(convertToResponseDTO(ti));
        }

        return responseList;
    }

}
