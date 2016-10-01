package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.ItemStock;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface ItemStockService {

    List<ItemStock> findAllItemStocks();

    ItemStock findItemStockById(Long id);

    ItemStock findItemStockByItemId(Long itemId);

    ItemStock saveItemStock(ItemStock itemStock);

    ItemStock updateItemStock(ItemStock itemStock);

}
