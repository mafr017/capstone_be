package com.madityafr.authservice.service;

import com.madityafr.authservice.dto.UserDTO;
import com.madityafr.authservice.entity.User;
import org.springframework.security.core.Authentication;

public interface TokenService {
    String generatedToken(Authentication authentication);
    UserDTO decodeToken(String token);
    User addUser(UserDTO userDTO);
    Integer countUser();
}
