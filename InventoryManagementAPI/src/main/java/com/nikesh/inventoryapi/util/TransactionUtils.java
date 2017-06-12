package com.nikesh.inventoryapi.util;

import com.nikesh.inventoryapi.dto.request.FileExportContent;
import com.nikesh.inventoryapi.dto.request.SingleTransactionBundleRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionSearchRequestParamDto;
import com.nikesh.inventoryapi.dto.response.SingleTransactionBundleResponseDTO;
import com.nikesh.inventoryapi.dto.response.TransactionDetailResponseDTO;
import com.nikesh.inventoryapi.dto.response.TransactionItemResponseDTO;
import com.nikesh.inventoryapi.dto.response.TransactionResponseDTO;
import com.nikesh.inventoryapi.entity.Item;
import com.nikesh.inventoryapi.service.ItemService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class TransactionUtils {

    public static String createItemWiseTxnSearchQueryForReport(TransactionSearchRequestParamDto searchRequest) {

        String query = "SELECT td.transaction_detail_id," // 0
                + "td.bill_no,"// 1
                + "t.party_id,"// 2
                + "t.transaction_date,"// 3
                + "t.total_amount,"// 4
                + "ti.item_id, "// 5
                + "ti.quantity"// 6
                + " FROM transaction_details td"
                + " JOIN transactions t"
                + " ON (td.transaction_id=t.transaction_id)"
                + " JOIN transaction_items ti"
                + " ON (td.transaction_detail_id=ti.transaction_detail_id)";

        String whereClause = " WHERE t.transaction_date BETWEEN :fromDate AND :toDate";
        if (searchRequest.getItemId() != null) {
            whereClause += " AND ti.item_id=:itemId";
        }

        if (searchRequest.getTransactionTypeId() != null) {
            whereClause += " AND t.transaction_type=:txnType";
        }

        System.out.println("QUERY :: " + query + whereClause);

        return query + whereClause;
    }

    public static java.sql.Date utilDateToSqlDate(Date uDate) {
        try {
            DateFormat sqlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            return java.sql.Date.valueOf(sqlDateFormatter.format(uDate));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<FileExportContent> convertSingleTxnBundleToFileExportContent(List<SingleTransactionBundleResponseDTO> txnBundleLists,
            ItemService itemService) {
        List<FileExportContent> result = new ArrayList<>();

        for (SingleTransactionBundleResponseDTO txn : txnBundleLists) {

            TransactionResponseDTO transaction = txn.getTransaction();
            TransactionDetailResponseDTO transactionDetail = txn.getTransactionDetail();
            List<TransactionItemResponseDTO> transactionItems = txn.getTransactionItems();

            for (TransactionItemResponseDTO transactionItem : transactionItems) {
                FileExportContent fileExportContent = new FileExportContent();
                fileExportContent.setAmount(transaction.getTotalAmount());
                fileExportContent.setBillNo(transactionDetail.getBillNo());

                Item itemById = itemService.findItemById(transactionItem.getItem());
                if (itemById != null) {
                    fileExportContent.setItemName(itemById.getItemName());
                }
                fileExportContent.setQuantity(transactionItem.getQuantity());
                fileExportContent.setTxnDate(transaction.getTransactionDate());

                result.add(fileExportContent);
            }
        }

        return result;
    }

}
