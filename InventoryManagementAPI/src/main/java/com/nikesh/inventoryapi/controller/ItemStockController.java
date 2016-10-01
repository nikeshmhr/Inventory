package com.nikesh.inventoryapi.controller;

import com.nikesh.inventoryapi.converter.ItemStockConverter;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.ItemStockResponseDTO;
import com.nikesh.inventoryapi.entity.ItemStock;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.ItemStockService;
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
@RequestMapping("/api/item-stocks")
public class ItemStockController {

    @Autowired
    private ItemStockService itemStockService;

    private List<ItemStock> itemStocks;

    private List<ItemStockResponseDTO> itemStockResponses;

    private ItemStock itemStock;

    private ItemStockResponseDTO itemStockResponseDTO;

    @RequestMapping(method = GET)
    public ResponseEntity<List<ItemStockResponseDTO>> getAllItemStocks() {
        try {
            // FETCHING ITEM STOCKS FROM DATABASE
            itemStocks = itemStockService.findAllItemStocks();

            if (!itemStocks.isEmpty()) {
                // CONVERT TO RESPONSE LIST.
                itemStockResponses = ItemStockConverter.convertToResponseDTOs(itemStocks);

                // RETURN RESPONSE
                return new ResponseEntity<>(itemStockResponses, HttpStatus.OK);
            } else {
                // THROW ERROR RESPONSE
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND,
                                "There aren't any item stock details to show.",
                                "Item stock details list returned empty."
                        )
                );
            }
        } catch (GenericException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericException(
                    new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Sorry, something went wrong while retrieving item stocks.",
                            "System problem, something went wrong please check log."
                    )
            );
        }
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<ItemStockResponseDTO> getItemStockById(@PathVariable("id") Long id) {
        try {
            // FETCHING ITEM STOCK BY ID.
            itemStock = itemStockService.findItemStockById(id);

            if (itemStock == null) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND,
                                "Item stock detail not found.",
                                "Item stock object returned null for provided id."
                        )
                );
            } else {
                // CONVERT TO RESPONSE DTO
                itemStockResponseDTO = ItemStockConverter.convertToResponseDTO(itemStock);

                // RETURN RESPONSE
                return new ResponseEntity<>(itemStockResponseDTO, HttpStatus.OK);
            }

        } catch (GenericException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericException(
                    new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Sorry, something went wrong while retrieving item stock.",
                            "System problem, something went wrong please check log."
                    )
            );
        }
    }

    @RequestMapping(value = "/items/{itemId}", method = GET)
    public ResponseEntity<ItemStockResponseDTO> getItemStockByItem(@PathVariable("itemId") Long itemId) {
        try {
            // FETCHING ITEM STOCK BY ITEM ID.
            itemStock = itemStockService.findItemStockByItemId(itemId);

            if (itemStock == null) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND,
                                "Item stock detail not found.",
                                "Item stock object returned null for provided item id."
                        )
                );
            } else {
                // CONVERT TO RESPONSE DTO
                itemStockResponseDTO = ItemStockConverter.convertToResponseDTO(itemStock);

                // RETURN RESPONSE
                return new ResponseEntity<>(itemStockResponseDTO, HttpStatus.OK);
            }

        } catch (GenericException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericException(
                    new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Sorry, something went wrong while retrieving item stock.",
                            "System problem, something went wrong please check log."
                    )
            );
        }
    }

}
