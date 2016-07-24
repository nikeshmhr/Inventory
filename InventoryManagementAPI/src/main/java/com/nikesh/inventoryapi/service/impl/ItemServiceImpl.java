package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.entity.Item;
import com.nikesh.inventoryapi.repository.ItemRepository;
import com.nikesh.inventoryapi.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nikesh Maharjan
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public Item findItemByItemName(String itemName) {
        return itemRepository.findItemByItemName(itemName);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.delete(itemId);
    }

}
