package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.Party;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nikesh Maharjan
 */
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findPartyByName(String name);

}
