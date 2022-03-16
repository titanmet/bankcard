package com.ratnikov.bankcard.mapper;

import com.ratnikov.bankcard.dto.RoleDTO;
import com.ratnikov.bankcard.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO roleToDTO(Role role);

    List<RoleDTO> toRoleDTOs(List<Role> roles);

    Role roleToModel(RoleDTO roleDTO);
}
