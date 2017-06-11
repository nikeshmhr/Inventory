package com.nikesh.inventoryapi.dto.request;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class FileExportParamDto implements Serializable {

    private List<FileExportContent> fileExportContent;

    private String documentTitle;

}
