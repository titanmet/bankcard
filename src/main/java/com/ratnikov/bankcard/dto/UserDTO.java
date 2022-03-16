package com.ratnikov.bankcard.dto;

import com.ratnikov.bankcard.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String name;
    private String lastName;
    private Boolean active;
    private Set<Role> roles;
}
