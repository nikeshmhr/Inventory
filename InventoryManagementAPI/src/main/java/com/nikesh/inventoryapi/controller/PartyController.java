package com.nikesh.inventoryapi.controller;

import com.nikesh.inventoryapi.converter.PartyConverter;
import com.nikesh.inventoryapi.dto.request.PartyRequestDTO;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.PartyResponseDTO;
import com.nikesh.inventoryapi.entity.Party;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.PartyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    private Party party;

    private List<Party> partyList;

    private List<PartyResponseDTO> partyResponseDTOList;

    private PartyResponseDTO partyResponseDTO;

    @RequestMapping(method = GET)
    public ResponseEntity<List<PartyResponseDTO>> getPartyList() {
        // RETURNS EXISTING Party LIST
        partyList = partyService.findAllParties();

        // CHECK IF LIST IS EMPTY OR NULL
        if (partyList == null || partyList.isEmpty()) {
            // THROW ErrorResponse
            throw new GenericException(new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    "Parties not found.",
                    "Parties not found. Party list returned empty.")
            );
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT INTO ItemResponseDTO LIST
                partyResponseDTOList = PartyConverter.convertToResponseDTOList(partyList);

                // RETURN RESPONSE WITH OK (200) STATUS
                return new ResponseEntity<>(partyResponseDTOList, HttpStatus.OK);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not read party list because of failed conversion.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity<PartyResponseDTO> addParty(@RequestBody PartyRequestDTO partyRequestDTO) {
        // CHECK IF REQUEST BODY IS NULL OR INVALID
        if (partyRequestDTO == null) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request body, please check your JSON request data format."
            ));
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT PartyRequestDTO (REQUEST BODY) INTO PERSISTABLE ENTITY OBJECT
                party = PartyConverter.convertToEntity(partyRequestDTO);

                // PERSIST CONVERTED ENTITY
                party = partyService.saveParty(party);

                // CONVERT PERSISTED OBJECT TO ItemResponseDTO OBJECT
                partyResponseDTO = PartyConverter.convertToResponseDTO(party);

                // RETURN RESPONSE WITH STATUS CREATED (201)
                return new ResponseEntity<>(partyResponseDTO, HttpStatus.CREATED);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not add add party because of conversion failure.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

}
