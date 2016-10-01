package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.ItemStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikesh Maharjan
 */
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {

    @Query("SELECT isi FROM ItemStock isi")
    List<ItemStock> findAllItemStocks();

    @Query("SELECT isi FROM ItemStock isi WHERE isi.item.id=:itemId")
    ItemStock findStockByItemId(@Param("itemId") Long itemId);

}
