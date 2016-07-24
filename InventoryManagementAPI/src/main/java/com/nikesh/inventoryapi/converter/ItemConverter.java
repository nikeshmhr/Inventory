package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.ItemRequestDTO;
import com.nikesh.inventoryapi.dto.response.ItemResponseDTO;
import com.nikesh.inventoryapi.entity.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class ItemConverter {

    /**
     * Method that converts the ItemRequestDTO object to its corresponding Item
     * object. This method is usually called before persisting information about
     * Item into database.
     *
     * @param itemRequestDTO Object to use for reference when converting to
     * entity.
     * @return Converted Item entity object.
     * @throws java.lang.Exception Could throw exception while converting. (One
     * of the exception might be NullPointerException)
     */
    public static Item convertToEntity(ItemRequestDTO itemRequestDTO) throws Exception {
        Item item = new Item();

        item.setCreatedDate(itemRequestDTO.getCreatedDate());
        item.setItemName(itemRequestDTO.getItemName());
        
        item.setPurchasePrice(itemRequestDTO.getPurchasePrice());
        item.setSalePrice(itemRequestDTO.getSalePrice());

        return item;
    }

    /**
     * Method that converts the Item entity object to its corresponding
     * ItemResponseDTO object. This method is usually called after performing
     * some operations (like CREATE, UPDATE, DELETE) in database and before
     * returning response.
     *
     * @param item Entity object to use for reference when converting to
     * response DTO object.
     * @return Converted ItemResponseDTO object.
     * @throws java.lang.Exception Might throw NullPointException and other
     * runtime exceptions.
     */
    public static ItemResponseDTO convertToResponseDTO(Item item) throws Exception {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();

        itemResponseDTO.setCreatedDate(item.getCreatedDate());
        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setItemName(item.getItemName());
        itemResponseDTO.setLastModifiedDate(item.getLastModifiedDate());
        itemResponseDTO.setPurchasePrice(item.getPurchasePrice());
        itemResponseDTO.setSalePrice(item.getSalePrice());

        return itemResponseDTO;
    }

    /**
     * Method that converts list of Item entity object to its corresponding list
     * of ItemResponseDTO object. This method makes use of
     * ItemConverter.converToResponseDTO(Item item) to convert individual Item
     * entity object, and adds each response object into ArrayList.
     *
     * @param items List of Item entity to convert to.
     * @return List of converted ItemResponseDTO.
     * @throws java.lang.Exception Could throw exception by item conversion
     * method.
     */
    public static List<ItemResponseDTO> convertToResponseDTOList(List<Item> items) throws Exception {
        List<ItemResponseDTO> itemResponseDTOs = new ArrayList<>();

        if (items == null || items.isEmpty()) {
            return itemResponseDTOs;
        } else {
            for (Item item : items) {
                itemResponseDTOs.add(convertToResponseDTO(item));
            }
        }

        return itemResponseDTOs;
    }

}
