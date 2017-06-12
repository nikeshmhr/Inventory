package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nikesh Maharjan
 */
@Getter
@Setter
public class FileExportParamDto implements Serializable {

    private List<FileExportContent> fileExportContent;

    private String documentTitle;
    
    private Date fromDate;
    
    private Date toDate;
    
    private String itemName;

}
