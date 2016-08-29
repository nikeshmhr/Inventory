package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.entity.TransactionDetail;
import com.nikesh.inventoryapi.repository.TransactionDetailRepository;
import com.nikesh.inventoryapi.service.TransactionDetailService;
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
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public List<TransactionDetail> findAllTransactionDetails() {
        return transactionDetailRepository.findAll();
    }

    @Override
    public TransactionDetail findTransactionDetailByID(Long transactionDetailID) {
        return transactionDetailRepository.findOne(transactionDetailID);
    }

    @Override
    public TransactionDetail findTransactionDetailByTransactionID(Long transactionID) {
        return transactionDetailRepository.findTransactionDetailByTransactionID(transactionID);
    }

    @Override
    public TransactionDetail findTransactionDetailByBillNo(String billNo) {
        return transactionDetailRepository.findTransactionDetailByBillNo(billNo);
    }

    @Override
    public TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    public TransactionDetail saveTransactionDetails(List<TransactionDetail> transactionDetails) {
        TransactionDetail transactionDetail = null;

        for (int i = 0; i < transactionDetails.size(); i++) {
            if ((i + 1) == transactionDetails.size()) {
                transactionDetail = saveTransactionDetail(transactionDetails.get(i));
            } else {
                saveTransactionDetail(transactionDetails.get(i));
            }
        }

        return transactionDetail;
    }

    @Override
    public TransactionDetail updateTransactionDetail(TransactionDetail modifiedTransactionDetail) {
        if (modifiedTransactionDetail.getId() == null) {
            return null;
        } else {
            return transactionDetailRepository.save(modifiedTransactionDetail);
        }
    }

    @Override
    public TransactionDetail updateTransactionDetails(List<TransactionDetail> modifiedTransactionDetails) {
        TransactionDetail transactionDetail = null;

        for (int i = 0; i < modifiedTransactionDetails.size(); i++) {
            if ((i + 1) == modifiedTransactionDetails.size()) {
                transactionDetail = updateTransactionDetail(modifiedTransactionDetails.get(i));
            } else {
                updateTransactionDetail(modifiedTransactionDetails.get(i));
            }
        }

        return transactionDetail;
    }

}
