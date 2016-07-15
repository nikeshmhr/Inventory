package com.nikesh.inventoryapi.converter;

import com.nikesh.inventoryapi.dto.request.UserRequestDTO;
import com.nikesh.inventoryapi.dto.response.UserResponseDTO;
import com.nikesh.inventoryapi.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nikesh Maharjan
 */
public class UserConverter {

    public static User convertToEntity(UserRequestDTO userRequestDTO) {
        User user = new User();

        user.setCreatedDate(userRequestDTO.getCreatedDate());
        user.setId(userRequestDTO.getId());
        user.setPassword(userRequestDTO.getPassword());
        user.setStatus(userRequestDTO.getStatus());
        user.setUserType(userRequestDTO.getUserType());
        user.setUsername(userRequestDTO.getUsername());

        return user;
    }

    public static UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setCreatedDate(user.getCreatedDate());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setStatus(user.getStatus());
        userResponseDTO.setUserType(user.getUserType());
        userResponseDTO.setUsername(user.getUsername());

        return userResponseDTO;
    }

    public static List<UserResponseDTO> convertToResponseDTOList(List<User> users) {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for (User user : users) {
            userResponseDTOList.add(convertToResponseDTO(user));
        }

        return userResponseDTOList;
    }

    public static boolean validatePOSTRequest(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getId() == null;
    }

}
