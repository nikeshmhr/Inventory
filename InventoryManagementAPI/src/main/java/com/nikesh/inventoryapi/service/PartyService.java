package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.Party;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface PartyService {

    /**
     * Method to find all existing parties.
     *
     * @return List of Party entity object.
     */
    List<Party> findAllParties();

    /**
     * Method to persist Party entity object into database.
     *
     * @param party Party entity object to persist.
     * @return Persisted Party entity object which also contains the new id
     * given after persisting.
     */
    Party saveParty(Party party);

    /**
     * Method to persist (update) Party entity object into database.
     *
     * @param party Party entity object to persist.
     * @return Persisted (Updated) entity object.
     */
    Party updateParty(Party party);

    /**
     * Method to find Party entity object by its id property.
     *
     * @param id The id using which to search Party entity.
     * @return Party object if found otherwise NULL.
     */
    Party findPartyById(Long id);

    /**
     * Method to find Party entity object by its name property.
     *
     * @param name The name using which to search Party entity.
     * @return List of Party entity object if found otherwise empty ArrayList.
     */
    List<Party> findPartyByName(String name);

    /**
     * Method to delete Party entity object by its ID property.
     *
     * @param partyId The id of a Party to deleted.
     */
    void deleteParty(Long partyId);

}
