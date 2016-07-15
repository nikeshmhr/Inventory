package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.Party;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface PartyService {

    List<Party> findAllParties();

    Party saveParty(Party party);

    Party updateParty(Party party);

    Party findPartyById(Long id);

    List<Party> findPartyByName(String name);

}
