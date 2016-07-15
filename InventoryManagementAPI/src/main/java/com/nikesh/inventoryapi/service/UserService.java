package com.nikesh.inventoryapi.service;

import com.nikesh.inventoryapi.entity.User;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public interface UserService {

    List<User> findAllUsers();

    User createUser(User user);

}
