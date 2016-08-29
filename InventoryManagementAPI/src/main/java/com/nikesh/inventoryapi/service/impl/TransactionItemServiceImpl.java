package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.entity.TransactionItem;
import com.nikesh.inventoryapi.repository.TransactionItemRepository;
import com.nikesh.inventoryapi.service.TransactionItemService;
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
public class TransactionItemServiceImpl implements TransactionItemService {

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    @Override
    public List<TransactionItem> findAllTransactionItems() {
        return transactionItemRepository.findAll();
    }

    @Override
    public TransactionItem findTransactionItemByID(Long id) {
        return transactionItemRepository.findOne(id);
    }

    @Override
    public List<TransactionItem> findTransactionItemsByTransactionDetailID(Long transactionDetailID) {
        return transactionItemRepository.findTransactionItemsByTransactionDetailID(transactionDetailID);
    }

    @Override
    public List<TransactionItem> findTransactionItemsByItemID(Long itemID) {
        return transactionItemRepository.findTransactionItemsByItemID(itemID);
    }

    @Override
    public TransactionItem saveTransactionItem(TransactionItem transactionItem) {
        return transactionItemRepository.save(transactionItem);
    }

    @Override
    public TransactionItem saveTransactionItems(List<TransactionItem> transactionItems) {
        TransactionItem transactionItem = null;

        for (int i = 0; i < transactionItems.size(); i++) {
            if ((i + 1) == transactionItems.size()) {
                transactionItem = saveTransactionItem(transactionItems.get(i));
            } else {
                saveTransactionItem(transactionItems.get(i));
            }
        }

        return transactionItem;
    }

    @Override
    public TransactionItem updateTransactionItem(TransactionItem modifiedTransactionItem) {
        if (modifiedTransactionItem.getId() == null) {
            return null;
        } else {
            return transactionItemRepository.save(modifiedTransactionItem);
        }
    }

    @Override
    public TransactionItem updateTransactionItems(List<TransactionItem> modifiedTransactionItems) {
        TransactionItem transactionItem = null;

        for (int i = 0; i < modifiedTransactionItems.size(); i++) {
            if ((i + 1) == modifiedTransactionItems.size()) {
                transactionItem = transactionItemRepository.save(modifiedTransactionItems.get(i));
            } else {
                transactionItemRepository.save(modifiedTransactionItems.get(i));
            }
        }

        return transactionItem;
    }

}
