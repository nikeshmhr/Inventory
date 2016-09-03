package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.TransactionDetailRequestDTO;
import com.nikesh.inventoryapi.dto.response.TransactionDetailResponseDTO;
import com.nikesh.inventoryapi.entity.TransactionDetail;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionDetailConverter {

    /**
     * Method that converts TransactionDetailRequestDTO object to
     * TransactionDetail entity object.
     *
     * @param requestDTO TransactionDetailRequestDTO object to convert.
     * @return Converted TransactionDetail entity object.
     * @throws Exception
     */
    public static TransactionDetail convertToEntity(TransactionDetailRequestDTO requestDTO) throws Exception {
        TransactionDetail td = new TransactionDetail();

        td.setId(requestDTO.getId());
        td.setBalance(requestDTO.getBalance());

        td.setBillNo(requestDTO.getBillNo());
        td.setDiscountAmount(requestDTO.getDiscountAmount());

        td.setDiscountPercent(requestDTO.getDiscountPercent());
        td.setPaidAmount(requestDTO.getPaidAmount());

        td.setSubTotal(requestDTO.getSubTotal());
        td.setTaxAmount(requestDTO.getTaxAmount());

        td.setTaxPercent(requestDTO.getTaxPercent());

        return td;
    }

    /**
     * Method that converts TransactionDetail entity object to
     * TransactionDetailResponseDTO.
     *
     * @param td The TransactionDetail entity object to convert.
     * @return Converted TransactionDetailResponseDTO object.
     * @throws Exception
     */
    public static TransactionDetailResponseDTO convertToResponseDTO(TransactionDetail td) throws Exception {
        TransactionDetailResponseDTO response = new TransactionDetailResponseDTO();

        response.setId(td.getId());
        response.setBalance(td.getBalance());

        response.setBillNo(td.getBillNo());
        response.setDiscountAmount(td.getDiscountAmount());

        response.setDiscountPercent(td.getDiscountPercent());
        response.setPaidAmount(td.getPaidAmount());

        response.setSubTotal(td.getSubTotal());
        response.setTaxAmount(td.getTaxAmount());

        response.setTaxPercent(td.getTaxPercent());
        response.setTransaction(td.getTransaction().getId());

        return response;
    }

    /**
     * Method that converts TransactionDetail entity object list to
     * TransactionDetailResponseDTO list. The method makes use of
     * convertToResponseDTO() method for converting every entity object on its
     * list.
     *
     * @param tds The TransactionDetail entity object list to convert.
     * @return Converted TransactionDtailResponseDTO list.
     * @throws Exception
     */
    public static List<TransactionDetailResponseDTO> convertToResponseDTOList(List<TransactionDetail> tds) throws Exception {
        List<TransactionDetailResponseDTO> responseList = new ArrayList<>();

        for (TransactionDetail td : tds) {
            responseList.add(convertToResponseDTO(td));
        }

        return responseList;
    }

}
