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

    public static Party convertToEntity(PartyRequestDTO partyRequestDTO) throws Exception {
        Party party = new Party();

        party.setBalanceAsOfDate(partyRequestDTO.getBalanceAsOfDate());
        party.setCurrentBalance(partyRequestDTO.getCurrentBalance());

        party.setEmail(partyRequestDTO.getEmail());
        party.setId(partyRequestDTO.getId());

        party.setName(partyRequestDTO.getName());
        party.setPartyType(partyRequestDTO.getPartyType());

        party.setPhoneNumber(partyRequestDTO.getPhoneNumber());

        return party;
    }

    public static PartyResponseDTO convertToResponseDTO(Party party) throws Exception {
        PartyResponseDTO partyResponseDTO = new PartyResponseDTO();

        partyResponseDTO.setBalanceAsOfDate(party.getBalanceAsOfDate());
        partyResponseDTO.setCurrentBalance(party.getCurrentBalance());

        partyResponseDTO.setEmail(party.getEmail());
        partyResponseDTO.setId(party.getId());

        partyResponseDTO.setName(party.getName());
        partyResponseDTO.setPartyType(party.getPartyType());

        partyResponseDTO.setPhoneNumber(party.getPhoneNumber());

        return partyResponseDTO;
    }

    public static List<PartyResponseDTO> convertToResponseDTOList(List<Party> parties) throws Exception {
        List<PartyResponseDTO> partyResponseDTOs = new ArrayList<>();

        for (Party party : parties) {
            partyResponseDTOs.add(convertToResponseDTO(party));
        }

        return partyResponseDTOs;
    }

}
