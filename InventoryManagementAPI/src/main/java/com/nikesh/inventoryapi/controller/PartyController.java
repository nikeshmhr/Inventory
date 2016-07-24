package com.nikesh.inventoryapi.controller;

import com.nikesh.inventoryapi.converter.PartyConverter;
import com.nikesh.inventoryapi.dto.request.PartyRequestDTO;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.PartyResponseDTO;
import com.nikesh.inventoryapi.entity.Party;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.PartyService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
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

    /**
     * Resource URI to get list of all parties.
     *
     * @return Response list of PartyResponseDTO.
     */
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
                // CONVERT INTO PartyResponseDTO LIST
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

    /**
     * Resource URI to get one party.
     *
     * @param partyId The ID of Party to search for.
     *
     * @return Response of PartyResponseDTO containing Party info.
     */
    @RequestMapping(method = GET, value = "/{partyId}")
    public ResponseEntity<PartyResponseDTO> getOneParty(@PathVariable("partyId") Long partyId) {
        // CHECK IF partyId IS INVALID
        if (partyId == null || partyId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request, invalid partyId. [" + partyId + "]."
            ));
        } else {
            // RETURNS EXISTING Party
            party = partyService.findPartyById(partyId);

            // CHECK IF Party IS NULL
            if (party == null) {
                // THROW ErrorResponse
                throw new GenericException(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND,
                        "Partys not found.",
                        "Partys not found. Party returned empty.")
                );
            } else {
                // TRY AND CATCH FOR ANY EXCEPTION
                try {
                    // CONVERT INTO PartyResponseDTO
                    partyResponseDTO = PartyConverter.convertToResponseDTO(party);

                    // RETURN RESPONSE WITH OK (200) STATUS
                    return new ResponseEntity<>(partyResponseDTO, HttpStatus.OK);
                } catch (Exception ex) {
                    throw new GenericException(new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Could not read party because of failed conversion.",
                            "Conversion failed. Exception: [" + ex.getMessage() + "]"
                    ));
                }
            }
        }
    }

    /**
     * Resource URI to create new resource of Party entity.
     *
     * @param partyRequestDTO RequestBody of type PartyRequestDTO which holds
     * the Party information to create.
     * @return Response object of type PartyResponseDTO containing information
     * that is created by using PartyRequestDTO (RequestBody) as a reference.
     */
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
                party.setId(null);
                party.setLastModifiedDate(new Date());

                // PERSIST CONVERTED ENTITY
                party = partyService.saveParty(party);

                // CONVERT PERSISTED OBJECT TO PartyResponseDTO OBJECT
                partyResponseDTO = PartyConverter.convertToResponseDTO(party);

                // RETURN RESPONSE WITH STATUS CREATED (201)
                return new ResponseEntity<>(partyResponseDTO, HttpStatus.CREATED);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not add party because of conversion failure.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    /**
     * Resource URI to update existing resource of Party entity.
     *
     * @param modifiedPartyRequestDTO RequestBody of type PartyRequestDTO which
     * holds the modified Party information to update.
     * @param partyId
     * @return Response object of type PartyResponseDTO containing information
     * that is updated by using PartyRequestDTO (RequestBody) as a reference.
     */
    @RequestMapping(method = PUT, value = "/{partyId}")
    public ResponseEntity<PartyResponseDTO> updateParty(@RequestBody PartyRequestDTO modifiedPartyRequestDTO, @PathVariable("partyId") Long partyId) {
        // CHECK IF REQUEST BODY IS INVALID
        if (modifiedPartyRequestDTO == null || partyId == null || partyId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request body, either invalid JSON request data format or party id."
            ));
        } else {
            // TRY AND CATCH FOR ANY EXCEPTION
            try {
                // CONVERT PartyRequestDTO (REQUEST BODY) INTO PERSISTABLE ENTITY OBJECT
                party = PartyConverter.convertToEntity(modifiedPartyRequestDTO);
                party.setId(partyId);
                party.setLastModifiedDate(new Date());

                // PERSIST (UPDATE) CONVERTED ENTITY
                party = partyService.updateParty(party);

                // CONVERT PERSISTED OBJECT TO PartyResponseDTO OBJECT
                partyResponseDTO = PartyConverter.convertToResponseDTO(party);

                // RETURN RESPONSE WITH STATUS OK (200)
                return new ResponseEntity<>(partyResponseDTO, HttpStatus.OK);
            } catch (Exception ex) {
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Could not update party because of conversion failure.",
                        "Conversion failed. Exception: [" + ex.getMessage() + "]"
                ));
            }
        }
    }

    /**
     * Resource URI to delete existing resource of Party entity.
     *
     * @param partyId The ID of a Party entity object to delete.
     * @return Response object of type PartyResponseDTO containing information
     * of deleted Party entity object.
     */
    @RequestMapping(method = DELETE, value = "/{partyId}")
    public ResponseEntity<PartyResponseDTO> deleteParty(@PathVariable("partyId") Long partyId) {
        // CHECK IF partyId IS INVALID
        if (partyId == null || partyId <= 0) {
            throw new GenericException(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "Invalid request body.",
                    "Invalid request, invalid party id [" + partyId + "]."
            ));
        } else {
            party = partyService.findPartyById(partyId);

            // CHECK IF Party WITH THIS ID EXISTS
            if (party == null) {
                // THROW ERROR RESPONSE
                throw new GenericException(new ErrorResponse(
                        HttpStatus.EXPECTATION_FAILED.value(),
                        HttpStatus.EXPECTATION_FAILED,
                        "Party with id [" + partyId + "] does not exists.",
                        "Failed to find party with id [" + partyId + "] to delete."
                ));
            } else {
                try {
                    // CONVERT Party TO BE DELETED INTO RESPONSE DTO
                    partyResponseDTO = PartyConverter.convertToResponseDTO(party);

                    // DELETE Party
                    partyService.deleteParty(partyId);

                    // RETURN RESPONSE
                    return new ResponseEntity<>(partyResponseDTO, HttpStatus.OK);
                } catch (Exception ex) {
                    // THROW CONVERSION EXCEPTION
                    throw new GenericException(new ErrorResponse(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED,
                            "Could not update party because of conversion failure.",
                            "Conversion failed. Exception: [" + ex.getMessage() + "]"
                    ));
                }
            }
        }
    }

}
