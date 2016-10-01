package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.ItemStockRequestDTO;
import com.nikesh.inventoryapi.dto.response.ItemStockResponseDTO;
import com.nikesh.inventoryapi.entity.ItemStock;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class ItemStockConverter {

    public static ItemStock converToEntity(ItemStockRequestDTO requestDTO) {
        ItemStock itemStock = new ItemStock();

        itemStock.setAvailableNoOfStocks(requestDTO.getAvailableNoOfStocks());
        itemStock.setId(requestDTO.getId());

        return itemStock;
    }

    public static ItemStockResponseDTO convertToResponseDTO(ItemStock entity) {
        ItemStockResponseDTO response = new ItemStockResponseDTO();

        response.setAvailableNoOfStocks(entity.getAvailableNoOfStocks());
        response.setCreatedDate(entity.getCreatedDate());
        response.setId(entity.getId());
        response.setItem(entity.getItem().getId());
        response.setLastModifiedDate(entity.getLastModifiedDate());

        return response;
    }

    public static List<ItemStockResponseDTO> convertToResponseDTOs(List<ItemStock> entities) {
        List<ItemStockResponseDTO> responses = new ArrayList<>();

        for (ItemStock entity : entities) {
            responses.add(convertToResponseDTO(entity));
        }

        return responses;
    }

}
