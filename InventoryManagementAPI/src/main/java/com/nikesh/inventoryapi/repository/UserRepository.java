package com.nikesh.inventoryapi.repository;

import com.nikesh.inventoryapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nikesh Maharjan
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
