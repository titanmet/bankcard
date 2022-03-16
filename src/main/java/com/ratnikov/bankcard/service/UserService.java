package com.ratnikov.bankcard.service;

import com.ratnikov.bankcard.dto.UserDTO;
import com.ratnikov.bankcard.mapper.UserMapper;
import com.ratnikov.bankcard.model.Role;
import com.ratnikov.bankcard.model.User;
import com.ratnikov.bankcard.repository.RoleRepository;
import com.ratnikov.bankcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.userToDTO(user);
    }

    public UserDTO findUserByUserNameDTO(String userName) {
        User user = userRepository.findByUserName(userName);
        return userMapper.userToDTO(user);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
}