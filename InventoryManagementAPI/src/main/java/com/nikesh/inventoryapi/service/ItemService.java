package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.Item;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface ItemService {

    /**
     * Method to find list of all existing items.
     *
     * @return List of Item entity object.
     */
    List<Item> findAllItems();

    /**
     * Method to persist Item entity object into database.
     *
     * @param item Item entity object to persist.
     * @return Persisted Item entity object which also contains the new id given
     * after persisting.
     */
    Item saveItem(Item item);

    /**
     * Method to persist (update) Item entity object into database.
     *
     * @param item Item entity object to persist (update).
     * @return Persisted (updated) Item entity object.
     */
    Item updateItem(Item item);

    /**
     * Method to find Item entity object by its id property.
     *
     * @param id The id using which to search Item entity.
     * @return Item entity object if found otherwise NULL.
     */
    Item findItemById(Long id);

    /**
     * Method to find Item entity object by its name property.
     *
     * @param itemName The name using which to search Item entity.
     * @return Item entity object if found otherwise NULL.
     */
    Item findItemByItemName(String itemName);

    void deleteItem(Long itemId);

}
