package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nikesh Maharjan
 */
@Getter
@Setter
public class FileExportContent implements Serializable {

    private Date txnDate;

    private String itemName;

    private Long itemId;// just for comparision

    private Integer quantity;

    private Double amount;

    private String billNo;

}
