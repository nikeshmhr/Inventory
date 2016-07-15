package com.nikesh.inventoryapi.service.impl;

import com.nikesh.inventoryapi.entity.Party;
import com.nikesh.inventoryapi.repository.PartyRepository;
import com.nikesh.inventoryapi.service.PartyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nikesh Maharjan
 */
@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Override
    public List<Party> findAllParties() {
        return partyRepository.findAll();
    }

    @Override
    public Party saveParty(Party party) {
        return partyRepository.save(party);
    }

    @Override
    public Party updateParty(Party party) {
        return partyRepository.save(party);
    }

    @Override
    public Party findPartyById(Long id) {
        return partyRepository.findOne(id);
    }

    @Override
    public List<Party> findPartyByName(String name) {
        return partyRepository.findPartyByName(name);
    }

}
