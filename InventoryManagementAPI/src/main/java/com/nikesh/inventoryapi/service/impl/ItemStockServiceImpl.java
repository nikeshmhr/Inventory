package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.entity.ItemStock;
import com.nikesh.inventoryapi.repository.ItemStockRepository;
import com.nikesh.inventoryapi.service.ItemStockService;
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
public class ItemStockServiceImpl implements ItemStockService {

    @Autowired
    private ItemStockRepository repo;

    @Override
    public List<ItemStock> findAllItemStocks() {
        return repo.findAllItemStocks();
    }

    @Override
    public ItemStock findItemStockById(Long id) {
        return repo.findOne(id);
    }

    @Override
    public ItemStock findItemStockByItemId(Long itemId) {
        return repo.findStockByItemId(itemId);
    }

    @Override
    public ItemStock saveItemStock(ItemStock itemStock) {
        return repo.save(itemStock);
    }

    @Override
    public ItemStock updateItemStock(ItemStock itemStock) {
        return repo.save(itemStock);
    }

}
