package com.nikesh.inventoryapi.controller;

import com.google.gson.Gson;
import com.nikesh.inventoryapi.constants.SystemConstants;
import com.nikesh.inventoryapi.converter.TransactionConverter;
import com.nikesh.inventoryapi.converter.TransactionDetailConverter;
import com.nikesh.inventoryapi.converter.TransactionItemConverter;
import com.nikesh.inventoryapi.dto.request.FileExportContent;
import com.nikesh.inventoryapi.dto.request.FileExportParamDto;
import com.nikesh.inventoryapi.dto.request.SingleTransactionBundleRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionItemRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionRequestDTO;
import com.nikesh.inventoryapi.dto.request.TransactionSearchRequestParamDto;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.SingleTransactionBundleResponseDTO;
import com.nikesh.inventoryapi.dto.response.TransactionResponseDTO;
import com.nikesh.inventoryapi.entity.Item;
import com.nikesh.inventoryapi.entity.Transaction;
import com.nikesh.inventoryapi.entity.TransactionDetail;
import com.nikesh.inventoryapi.entity.TransactionItem;
import com.nikesh.inventoryapi.entity.TransactionType;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.ItemService;
import com.nikesh.inventoryapi.service.PartyService;
import com.nikesh.inventoryapi.service.SingleTransactionService;
import com.nikesh.inventoryapi.service.TransactionTypeService;
import com.nikesh.inventoryapi.util.TransactionUtils;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nikesh Maharjan
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private SingleTransactionService singleTransactionService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ServletContext context;

    private TransactionRequestDTO transactionRequestDTO;

    private List<TransactionItemRequestDTO> transactionItemRequestDTOs;

    private TransactionItem transactionItem;

    private List<TransactionItem> transactionItems;

    private TransactionDetail transactionDetail;

    private Transaction transaction;

    private TransactionResponseDTO transactionResponseDTO;

    /**
     * Resource URI to get all existing transactions.
     *
     * @return List of SingleTransactionBundleResponseDTO containing details of
     * transaction.
     */
    @RequestMapping(method = GET)
    public ResponseEntity<List<SingleTransactionBundleResponseDTO>> getAllTransactions() {
        try {
            List<SingleTransactionBundleResponseDTO> allTransactions = singleTransactionService.findAllTransactions();

            Gson gson = new Gson();

            if (allTransactions.isEmpty()) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND,
                                "Transaction not found.",
                                "Transaction list returned empty."
                        )
                );
            } else {
                System.out.println("JSON: " + gson.toJson(allTransactions.get(0)));

                return new ResponseEntity<>(allTransactions, HttpStatus.OK);
            }
        } catch (GenericException ge) {
            throw ge;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Something went wrong while retrieving transactions.",
                            "Expectation failed may be due to failed conversion."
                    )
            );
        }
    }

    /**
     * Resource URI to create/post/add new transaction.
     *
     * @param transReq The SingleTransactionBundleRequestDTO object containing
     * the details of transaction.
     * @return TransactionResponseDTO object containing basic transaction
     * information of created transaction.
     */
    @RequestMapping(method = POST)
    public ResponseEntity<TransactionResponseDTO> addTransaction(
            @RequestBody SingleTransactionBundleRequestDTO transReq) {
        try {
            // EXTRACT TransactionRequestDTO OBJECT.
            transactionRequestDTO = transReq.getTransaction();

            // CONVERT IT INTO Transaction ENTITY OBJECT
            // SET PARTY
            // SET TRANSACTION TYPE
            transaction = TransactionConverter.convertToEntity(transactionRequestDTO);
            transaction.setParty(partyService.findPartyById(transactionRequestDTO.getParty()));
            transaction.setTransactionType(transactionTypeService.findTransactionTypeById(transactionRequestDTO.getTransactionType()));

            // CONVERT TransactionDetailRequestDTO TO TransactionDetail ENTITY OBJECT.
            transactionDetail = TransactionDetailConverter.convertToEntity(transReq.getTransactionDetail());

            // EXTRACT TRANSACTION ITEMS ONLY IF TRANSACTION TYPE IS PURCHASE OR SALE 
            if (transaction.getTransactionType().getId() == SystemConstants.TransactionTypeConstants.PURCHASE
                    || transaction.getTransactionType().getId() == SystemConstants.TransactionTypeConstants.SALE) {
                // EXTRACT TransactionItemRequestDTO LIST.
                // INITIALIZE TransactionItem ENTITY LIST.
                transactionItemRequestDTOs = transReq.getTransactionItems();
                transactionItems = new ArrayList<>();

                // ITERATE THROUGH EVERY TransactionItemRequestDTO OBJECT
                for (TransactionItemRequestDTO reqDTO : transactionItemRequestDTOs) {
                    // CONVERT CURRENT TransactionItemRequestDTO OBJECT INTO TransactionItem ENTITY OBJECT
                    // SET ITEM PROPERTY
                    transactionItem = TransactionItemConverter.convertToEntity(reqDTO);
                    transactionItem.setItem(itemService.findItemById(reqDTO.getItem()));

                    // ADD CONVERTED TransactionItem ENTITY OBJECT TO TransactionItem ENTITY OBJECT LIST
                    transactionItems.add(transactionItem);
                }
            }

            // PERSIST TRANSACTION AND EXPECT Transaction ENTITY OBJECT.
            Transaction savedTransaction = singleTransactionService.addTransaction(transaction, transactionDetail, transactionItems);

            // CHECK IF savedTransaction OBJECT IS NULL OR IF ITS ID IS NULL
            // IF SO THEN THROW EXCEPTION.
            if (savedTransaction == null || savedTransaction.getId() == null) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.EXPECTATION_FAILED.value(),
                                HttpStatus.EXPECTATION_FAILED,
                                "Failed to add transaction.",
                                "Failed to add transaction. The persisted transaction object was either null or its ID was null."
                        )
                );
            } else {    // OTHERWISE
                // CONVERT PERSISTED Transaction ENTITY OBJECT TO TransactionResponseDTO OBJECT
                transactionResponseDTO = TransactionConverter.convertToResponseDTO(savedTransaction);

                return new ResponseEntity<>(transactionResponseDTO, HttpStatus.CREATED);
            }

        } catch (GenericException gex) {
            throw gex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Something went wrong while persisting or retriving persisted transactions.",
                            "Expectation failed may be due to failed conversion or save."
                    )
            );
        }
    }

    @RequestMapping(method = POST, value = "/search")
    public ResponseEntity<TransactionResponseDTO> searchTransaction(@RequestBody TransactionSearchRequestParamDto searchRequest) {
        // todo write this....
        return null;
    }

    @RequestMapping(method = POST, value = "/export")
    public ResponseEntity<byte[]> exportItemWiseTxnReport(@RequestBody TransactionSearchRequestParamDto searchRequest) throws FileNotFoundException {
        List<SingleTransactionBundleResponseDTO> exportResults = singleTransactionService.searchTxnToExport(searchRequest);

        if (!exportResults.isEmpty()) {
            FileExportParamDto fileExportParamDto = new FileExportParamDto();

            TransactionType findTransactionTypeById = transactionTypeService.findTransactionTypeById(searchRequest.getTransactionTypeId());
            fileExportParamDto.setDocumentTitle(findTransactionTypeById.getName() + " Report");
            fileExportParamDto.setFromDate(searchRequest.getFromDate());
            fileExportParamDto.setToDate(searchRequest.getToDate());
            Item findItemById = itemService.findItemById(searchRequest.getItemId());
            fileExportParamDto.setItemName(findItemById.getItemName());

            List<FileExportContent> exportContent = TransactionUtils.convertSingleTxnBundleToFileExportContent(exportResults, itemService);
            fileExportParamDto.setFileExportContent(exportContent);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", ";filename=file.xls");

            try {
                // GENERATE EXCEL FILE AND STORE IT IN ByteArrayOutputStream
                ByteArrayOutputStream generatedReportByte = generateReportExcel(fileExportParamDto);
                byte[] byteArray = generatedReportByte.toByteArray();

                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } catch (JRException ex) {
                System.out.println("Exception: " + ex.getMessage());
                return null;
            }
        } else {
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND,
                            "Transaction not found.",
                            "Transaction list returned empty."
                    )
            );
        }

    }

    private ByteArrayOutputStream generateReportExcel(FileExportParamDto exportParamDTO) throws JRException, FileNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // LOCATION OF JASPER REPOTR TEMPLATE FILE.
        String string = context.getRealPath("/reporting/individual-report-EXCEL.jrxml");

        // READ TEMPLATE AS INPUT STREAM
        InputStream fileRead = new FileInputStream(string);

        JasperDesign design = JRXmlLoader.load(fileRead);
        JasperReport report = JasperCompileManager.compileReport(design);

        // LOAD STATIC NKC LOGO.
        // POPULATE IT INTO CORRESPONDING PARAMETER.
//        String imageLogoFilePath = context.getRealPath("/static/images/report.png");
        Map hParam = new HashMap();
//        hParam.put("logo", imageLogoFilePath);
//        hParam.put("logo", imageLogoFilePath);
        hParam.put("documentTitle", exportParamDTO.getDocumentTitle());
        hParam.put("itemName", exportParamDTO.getItemName());
        hParam.put("fromDate", exportParamDTO.getFromDate());
        hParam.put("toDate", exportParamDTO.getToDate());

        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(exportParamDTO.getFileExportContent());
        JasperPrint print = JasperFillManager.fillReport(report, hParam, jrbcds);

        JRXlsExporter exporter = new JRXlsExporter();

        // Here we assign the parameters jp and baos to the exporter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_IMAGE_BORDER_FIX_ENABLED, Boolean.TRUE);

        try {
            exporter.exportReport();
            baos.close();

            return baos;
        } catch (JRException | IOException ex) {
            ex.printStackTrace();
        }

        return baos;

    }

    @RequestMapping(method = POST, value = "/export/all")
    public ResponseEntity<byte[]> exportAllItemTxnReport(@RequestBody TransactionSearchRequestParamDto searchRequestParamDto) throws FileNotFoundException {

        searchRequestParamDto.setItemId(null);

        List<FileExportContent> exportContent = singleTransactionService.getTxnByTxnTypeAndTxnDate(searchRequestParamDto);

        if (!exportContent.isEmpty()) {
            FileExportParamDto exportParam = new FileExportParamDto();
            TransactionType findTransactionTypeById = transactionTypeService.findTransactionTypeById(searchRequestParamDto.getTransactionTypeId());
            exportParam.setDocumentTitle(findTransactionTypeById.getName() + " Report");
            exportParam.setFileExportContent(exportContent);
            exportParam.setFromDate(searchRequestParamDto.getFromDate());
            exportParam.setToDate(searchRequestParamDto.getToDate());

            try {
                // GENERATE EXCEL FILE AND STORE IT IN ByteArrayOutputStream
                ByteArrayOutputStream generatedReportByte = generateReportExcelAll(exportParam);
                byte[] byteArray = generatedReportByte.toByteArray();

                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } catch (JRException ex) {
                System.out.println("Exception: " + ex.getMessage());
                return null;
            }
        } else {
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND,
                            "Transaction not found.",
                            "Transaction list returned empty."
                    )
            );
        }
    }

    private ByteArrayOutputStream generateReportExcelAll(FileExportParamDto exportParamDTO) throws FileNotFoundException, JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // LOCATION OF JASPER REPOTR TEMPLATE FILE.
        String string = context.getRealPath("/reporting/group-report-EXCEL.jrxml");

        // READ TEMPLATE AS INPUT STREAM
        InputStream fileRead = new FileInputStream(string);

        JasperDesign design = JRXmlLoader.load(fileRead);
        JasperReport report = JasperCompileManager.compileReport(design);

        // LOAD STATIC NKC LOGO.
        // POPULATE IT INTO CORRESPONDING PARAMETER.
//        String imageLogoFilePath = context.getRealPath("/static/images/report.png");
        Map hParam = new HashMap();
//        hParam.put("logo", imageLogoFilePath);
//        hParam.put("logo", imageLogoFilePath);
        hParam.put("documentTitle", exportParamDTO.getDocumentTitle());
        hParam.put("fromDate", exportParamDTO.getFromDate());
        hParam.put("toDate", exportParamDTO.getToDate());

        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(exportParamDTO.getFileExportContent());
        JasperPrint print = JasperFillManager.fillReport(report, hParam, jrbcds);

        JRXlsExporter exporter = new JRXlsExporter();

        // Here we assign the parameters jp and baos to the exporter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_IMAGE_BORDER_FIX_ENABLED, Boolean.TRUE);

        try {
            exporter.exportReport();
            baos.close();

            return baos;
        } catch (JRException | IOException ex) {
            ex.printStackTrace();
        }

        return baos;
    }

}
