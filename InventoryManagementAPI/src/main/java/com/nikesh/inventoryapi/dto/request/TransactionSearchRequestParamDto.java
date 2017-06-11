package com.nikesh.inventoryapi.dto.request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nikesh Maharjan
 */
@Getter
@Setter
public class TransactionSearchRequestParamDto {

    private Date fromDate;

    private Date toDate;

    private Long transactionTypeId;

    private Long itemId;

    private String fileType;

}
