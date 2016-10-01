package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.Party;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikesh Maharjan
 */
public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findPartyByName(String name);

    @Query("SELECT p FROM Party p WHERE p.partyType=:partyType")
    List<Party> findByPartyType(@Param("partyType") Character partyType);

}
