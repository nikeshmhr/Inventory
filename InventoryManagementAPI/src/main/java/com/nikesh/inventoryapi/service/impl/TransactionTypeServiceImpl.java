package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.dto.response.TransactionTypeResponseDTO;
import com.nikesh.inventoryapi.entity.TransactionType;
import com.nikesh.inventoryapi.repository.TransactionTypeRepository;
import com.nikesh.inventoryapi.service.TransactionTypeService;
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
public class TransactionTypeServiceImpl implements TransactionTypeService {

    @Autowired
    private TransactionTypeRepository repository;

    @Override
    public List<TransactionType> findAllTransactionTypes() {
        return repository.findAll();
    }

    @Override
    public TransactionType findTransactionTypeById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public TransactionType findTransactionTypeByName(String name) {
        return repository.findByName(name);
    }

}
