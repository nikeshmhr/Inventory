package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.Item;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface ItemService {

    List<Item> findAllItems();

    Item saveItem(Item item);

    Item updateItem(Item item);

    Item findItemById(Long id);

    Item findItemByItemName(String itemName);

}
