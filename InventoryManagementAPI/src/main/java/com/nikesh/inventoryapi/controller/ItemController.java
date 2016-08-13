package com.nikesh.inventoryapi.controller;

import com.nikesh.inventoryapi.converter.ItemConverter;
import com.nikesh.inventoryapi.dto.request.ItemRequestDTO;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.ItemResponseDTO;
import com.nikesh.inventoryapi.entity.Item;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.ItemService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nikesh Maharjan
 */
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    private Item item;

    private List<Item> itemList;

    private List<ItemResponseDTO> itemResponseDTOList;

    private ItemResponseDTO itemResponseDTO;

    /**
     * Resource URI to get list of all items.
     *
     * @return Response list of ItemResponseDTO.
     */
    @RequestMapping(method = GET)
    public ResponseEntity<List<ItemResponseDTO>> getItemList() {
        // RETURNS EXISTING Item LIST
        itemList = itemService.findAllItems();

        // CHECK IF LIST IS NULL OR EMPTY
        if (itemList == null || itemList.isEmpty()) {
            // THROW ErrorResponse
            throw new GenericException(new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Items not found.",
                    "Items not found. Item list returned empty.")
            );
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT INTO ItemResponseDTO LIST
                itemResponseDTOList = ItemConverter.convertToResponseDTOList(itemList);

                // RETURN RESPONSE WITH OK (200) STATUS
                return new ResponseEntity<>(itemResponseDTOList, HttpStatus.OK);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not read item list because of failed conversion.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    /**
     * Resource URI to get an item.
     *
     * @param itemId The ID of an item to retrieve.
     * @return Response of ItemResponseDTO containing Item info.
     */
    @RequestMapping(method = GET, value = "/{itemId}")
    public ResponseEntity<ItemResponseDTO> getOneItem(@PathVariable("itemId") Long itemId) {
        // CHECK IF itemId IS INVALID
        if (itemId == null || itemId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request, invalid itemId. [" + itemId + "]."
            ));
        } else {
            // RETURNS EXISTING Item
            item = itemService.findItemById(itemId);

            // CHECK IF Item IS NULL
            if (item == null) {
                // THROW ErrorResponse
                throw new GenericException(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND,
                        "Items not found.",
                        "Items not found. Item returned empty.")
                );
            } else {
                // TRY AND CATCH FOR ANY EXCEPTION
                try {
                    // CONVERT INTO ItemResponseDTO
                    itemResponseDTO = ItemConverter.convertToResponseDTO(item);

                    // RETURN RESPONSE WITH OK (200) STATUS
                    return new ResponseEntity<>(itemResponseDTO, HttpStatus.OK);
                } catch (Exception ex) {
                    throw new GenericException(new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Could not read item because of failed conversion.",
                            "Conversion failed. Exception: [" + ex.getMessage() + "]"
                    ));
                }
            }
        }
    }

    /**
     * Resource URI to create new resource of Item entity.
     *
     * @param itemRequestDTO RequestBody of type ItemRequestDTO which holds the
     * Item information to create.
     * @return Response object of type ItemResponseDTO containing information
     * that is created by using ItemRequestDTO (RequestBody) as a reference.
     */
    @RequestMapping(method = POST)
    public ResponseEntity<ItemResponseDTO> addItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        // CHECK IF REQUEST BODY IS NULL OR INVALID
        if (itemRequestDTO == null) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request body, please check your JSON request data format."
            ));
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT ItemRequestDTO (REQUEST BODY) INTO PERSISTABLE ENTITY OBJECT
                item = ItemConverter.convertToEntity(itemRequestDTO);
                item.setLastModifiedDate(new Date());
                item.setId(null);
                item.setCreatedDate(new Date());

                // PERSIST CONVERTED ENTITY
                item = itemService.saveItem(item);

                // CONVERT PERSISTED OBJECT TO ItemResponseDTO OBJECT
                itemResponseDTO = ItemConverter.convertToResponseDTO(item);

                // RETURN RESPONSE WITH STATUS CREATED (201)
                return new ResponseEntity<>(itemResponseDTO, HttpStatus.CREATED);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not add item because of conversion failure.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    /**
     * Resource URI to update existing resource of Item entity.
     *
     * @param modifiedItemRequestDTO RequestBody of type ItemRequestDTO which
     * holds the modified Item information to update.
     * @param itemId
     * @return Response object of type ItemResponseDTO containing information
     * that is updated by using ItemRequestDTO (RequestBody) as a reference.
     */
    @RequestMapping(method = PUT, value = "/{itemId}")
    public ResponseEntity<ItemResponseDTO> updateItem(@RequestBody ItemRequestDTO modifiedItemRequestDTO, @PathVariable("itemId") Long itemId) {
        // CHECK IF REQUEST IS VALID OR NOT
        if (modifiedItemRequestDTO == null || itemId == null || itemId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request body, either invalid JSON request data format or item id."
            ));
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT ItemRequestDTO (REQUEST BODY) INTO PERSISTABLE ENTITY OBJECT
                item = ItemConverter.convertToEntity(modifiedItemRequestDTO);
                item.setId(itemId);
                item.setLastModifiedDate(new Date());

                // PERSIST (UPDATE) CONVERTED ENTITY
                item = itemService.updateItem(item);

                // CONVERT PERSISTED OBJECT TO ItemResponseDTO OBJECT
                itemResponseDTO = ItemConverter.convertToResponseDTO(item);

                // RETURN RESPONSE WITH STATUS OK (200)
                return new ResponseEntity<>(itemResponseDTO, HttpStatus.OK);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not update item because of conversion failure.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    /**
     * Resource URI to delete existing resource of Item entity.
     *
     * @param itemId The id of an Item to delete.
     * @return Response object of type ItemResponseDTO containing information of
     * deleted Item.
     */
    @RequestMapping(method = DELETE, value = "/{itemId}")
    public ResponseEntity<ItemResponseDTO> deleteItem(@PathVariable("itemId") Long itemId) {
        // HttpHeaders headers = new HttpHeaders();
        // headers.setAccessControlAllowMethods(Arrays.asList(HeaderMethodConstants.ACCESS_CONTROL_ALLOW_METHODS));

        // CHECK IF itemId IS INVALID
        if (itemId == null || itemId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request, invalid item id [" + itemId + "]."
            ));
        } else {
            item = itemService.findItemById(itemId);

            // CHECK IF Item WITH THIS ID EXISTS
            if (item == null) {
                // THROW ERROR RESPONSE
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Item with id [" + itemId + "] does not exists.",
                        "Failed to find item with id [" + itemId + "] to delete."
                ));
            } else {
                try {
                    // CONVERT Item TO BE DELETED INTO RESPONSE DTO
                    itemResponseDTO = ItemConverter.convertToResponseDTO(item);

                    // DELETE Item
                    itemService.deleteItem(itemId);

                    // RETURN RESPONSE
                    return new ResponseEntity<>(itemResponseDTO, HttpStatus.OK);
                } catch (Exception ex) {
                    // THROW CONVERSION EXCEPTION
                    throw new GenericException(new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Could not update item because of conversion failure.",
                            "Conversion failed. Exception: [" + ex.getMessage() + "]"
                    ));
                }
            }
        }
    }

}
