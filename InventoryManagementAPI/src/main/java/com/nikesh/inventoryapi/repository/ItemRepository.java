package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nikesh Maharjan
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByItemName(String itemName);

}
