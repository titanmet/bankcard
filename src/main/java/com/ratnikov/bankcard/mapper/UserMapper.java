package com.ratnikov.bankcard.mapper;

import com.ratnikov.bankcard.dto.UserDTO;
import com.ratnikov.bankcard.model.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User userToModel(UserDTO userDTO);

    UserDTO userToDTO(Optional<User> user);
}
