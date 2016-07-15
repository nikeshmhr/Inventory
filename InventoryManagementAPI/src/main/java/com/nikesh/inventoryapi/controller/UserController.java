package com.nikesh.inventoryapi.controller;

import com.nikesh.inventoryapi.dto.request.UserRequestDTO;
import com.nikesh.inventoryapi.dto.response.ErrorResponse;
import com.nikesh.inventoryapi.dto.response.UserResponseDTO;
import com.nikesh.inventoryapi.entity.User;
import com.nikesh.inventoryapi.exception.GenericException;
import com.nikesh.inventoryapi.service.UserService;
import com.nikesh.inventoryapi.converter.UserConverter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nikesh Maharjan
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // STORES LIST OF UserResponseDTO
    private List<UserResponseDTO> userResponseDTOList;

    // STORES LIST OF User ENTITY
    private List<User> userList;

    // STORES User ENTITY
    private User user;

    // STORES UserResponseDTO
    private UserResponseDTO userResponseDTO;

    @RequestMapping(value = "/users", method = POST)
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        // CHECK FOR EXCEPTION CASE (E.g: JSON request with id property is set to some value)
        boolean validRequest = UserConverter.validatePOSTRequest(userRequestDTO);

        if (validRequest) {
            // CONVERT REQUEST DTO INTO ENTITY
            user = UserConverter.convertToEntity(userRequestDTO);

            try {
                // PERSIST User INTO DATABASE
                user = userService.createUser(user);

                // CHECK IF DATA IS PERSISTED CORRECTLY
                if (user.getId() == null) {
                    throw new GenericException(
                            new ErrorResponse(
                                    HttpStatus.EXPECTATION_FAILED.value(),
                                    HttpStatus.EXPECTATION_FAILED,
                                    "Sorry, something went wrong while creating user. Please try again later.",
                                    "Failed to create user because the user id was null after create operation."
                            )
                    );
                } else {
                    // CONVERT PERSISTED ENTITY TO RESPONSE DTO
                    userResponseDTO = UserConverter.convertToResponseDTO(user);

                    return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
                }
            } catch (Exception ex) {
                throw new GenericException(
                        new ErrorResponse(
                                HttpStatus.EXPECTATION_FAILED.value(),
                                HttpStatus.EXPECTATION_FAILED,
                                "Sorry, something went wrong while creating user. Please try again later.",
                                "Failed to create user. CAUSE: " + ex.getMessage() + ". Please view log for more information."
                        )
                );
            }

        } else {
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_GATEWAY,
                            "Bad request.",
                            "Bad request, please check your JSON request format."
                    )
            );
        }

    }

    @RequestMapping(value = "/users", method = GET)
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        // RETRIEVE ALL EXISTING USERS
        userList = userService.findAllUsers();

        // CHECK IF RESPONSE LIST IS EMPTY
        if (userList.isEmpty()) {
            throw new GenericException(
                    new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND,
                            "Users not found.",
                            "Users not found, user response list returned empty."
                    )
            );
        } else {
            // CONVERT ENTITY TO RESPONSE DTO
            userResponseDTOList = UserConverter.convertToResponseDTOList(userList);

            return new ResponseEntity<>(userResponseDTOList, HttpStatus.OK);
        }
    }

}
