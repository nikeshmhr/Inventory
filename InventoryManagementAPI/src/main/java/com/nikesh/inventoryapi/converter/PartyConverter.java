package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.PartyRequestDTO;
import com.nikesh.inventoryapi.dto.response.PartyResponseDTO;
import com.nikesh.inventoryapi.entity.Party;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class PartyConverter {

    /**
     * Method that converts PartyRequestDTO object into its corresponding Party
     * entity object. This method is usually called for before persisting
     * information about Party into database.
     *
     * @param partyRequestDTO Object to use as reference while converting.
     * @return Converted Party entity object.
     * @throws Exception Could throw exception while converting. (E.g:
     * NullPointerException)
     */
    public static Party convertToEntity(PartyRequestDTO partyRequestDTO) throws Exception {
        Party party = new Party();

        party.setBalanceAsOfDate(partyRequestDTO.getBalanceAsOfDate());
        party.setCurrentBalance(partyRequestDTO.getCurrentBalance());

        party.setEmail(partyRequestDTO.getEmail());

        party.setName(partyRequestDTO.getName());
        party.setPartyType(partyRequestDTO.getPartyType());

        party.setPhoneNumber(partyRequestDTO.getPhoneNumber());
        party.setCreatedDate(partyRequestDTO.getCreatedDate());

        return party;
    }

    /**
     * Method that converts Party entity object into its corresponding
     * PartyResponseDTO object. This method is usually called after some
     * database operations (like CREATE, UPDATE, DELETE) but before return
     * response.
     *
     * @param party Object to use as reference while converting.
     * @return Converted PartyResponseDTO object.
     * @throws Exception Could throw NullPointerException and other exceptions.
     */
    public static PartyResponseDTO convertToResponseDTO(Party party) throws Exception {
        PartyResponseDTO partyResponseDTO = new PartyResponseDTO();

        partyResponseDTO.setBalanceAsOfDate(party.getBalanceAsOfDate());
        partyResponseDTO.setCurrentBalance(party.getCurrentBalance());

        partyResponseDTO.setEmail(party.getEmail());
        partyResponseDTO.setId(party.getId());

        partyResponseDTO.setName(party.getName());
        partyResponseDTO.setPartyType(party.getPartyType());

        partyResponseDTO.setPhoneNumber(party.getPhoneNumber());
        partyResponseDTO.setCreatedDate(party.getCreatedDate());

        partyResponseDTO.setLastModifiedDate(party.getLastModifiedDate());

        return partyResponseDTO;
    }

    /**
     * Method that converts list of Party entity object to its corresponding
     * list of PartyResponseDTO object. This method makes use of
     * PartyConverter.converToResponseDTO(Party party) to convert individual
     * Party entity object, and adds each response object into ArrayList.
     *
     * @param parties List of Party entity object to convert.
     * @return List of converted PartyResponseDTO object.
     * @throws Exception
     */
    public static List<PartyResponseDTO> convertToResponseDTOList(List<Party> parties) throws Exception {
        List<PartyResponseDTO> partyResponseDTOs = new ArrayList<>();

        for (Party party : parties) {
            partyResponseDTOs.add(convertToResponseDTO(party));
        }

        return partyResponseDTOs;
    }

}
