package com.madityafr.authservice.service.impl;

import com.madityafr.authservice.dto.UserDTO;
import com.madityafr.authservice.entity.User;
import com.madityafr.authservice.exception.InvalidPasswordException;
import com.madityafr.authservice.exception.UserNotFoundException;
import com.madityafr.authservice.repository.UserRepository;
import com.madityafr.authservice.service.TokenService;
import com.madityafr.authservice.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    @Override
    public String generatedToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(""));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public UserDTO decodeToken(String token) {
        String newToken = token.split(" ")[1];
        Jwt jwtToken = jwtDecoder.decode(newToken);
        String data = jwtToken.getSubject();
        Optional<User> user = userRepository.findByUsername(data);
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDTO.class);
        }
        throw new UserNotFoundException("user not found");
    }

    @Override
    @Transactional()
    public User addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (!PasswordValidator.isValid(user.getPassword())) {
            throw new InvalidPasswordException("Password must be should contains:\n-at least 1 lowercase\n-at least 1 uppercase\n-at least 1 number\n-at least 1 special character");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
