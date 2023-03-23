package com.madityafr.authservice.controller;

import com.madityafr.authservice.dto.LoginResponseDTO;
import com.madityafr.authservice.dto.ResponseDTO;
import com.madityafr.authservice.dto.UserDTO;
import com.madityafr.authservice.entity.User;
import com.madityafr.authservice.service.TokenService;
import com.madityafr.authservice.service.impl.JpaUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailService jpaUserDetailService;

//    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getRole() == null) userDTO.setRole("user");
        User user = tokenService.addUser(userDTO);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("success add user")
                .data(user).build(), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> token(@RequestBody User user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        String token = tokenService.generatedToken(authentication);

        UserDTO dataUser = tokenService.decodeToken("Bearer "+token);
        LoginResponseDTO responseDTO = new LoginResponseDTO(dataUser.getId(), dataUser.getUsername(), dataUser.getEmail(), dataUser.getFirstName(), dataUser.getLastName(), dataUser.getRole(), token);

        return new ResponseEntity(ResponseDTO.builder().httpStatus(HttpStatus.OK).message("token created").data(responseDTO).build(), HttpStatus.OK);

    }
    @GetMapping("/user-data")
    public ResponseEntity<ResponseDTO<Object>> userInfo(@RequestHeader(name = "Authorization") String tokenBearer) {

        UserDTO user = tokenService.decodeToken(tokenBearer);

        //add deeper structure
        return new ResponseEntity(ResponseDTO.builder().httpStatus(HttpStatus.OK).message("token found").data(user).build(), HttpStatus.OK);
        // return user;
    }
    //user
    @GetMapping("/user-data-2")
    public ResponseEntity<UserDTO> userInfo2(@RequestHeader(name = "Authorization") String tokenBearer) {
        UserDTO user = tokenService.decodeToken(tokenBearer);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDTO>> allUsers() {
        log.info("AuthController method allUsers");
        return new ResponseEntity<>(jpaUserDetailService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/count-users")
    public ResponseEntity<Integer> countUsers() {
        log.info("count users");
        Integer countUser = tokenService.countUser();
        return new ResponseEntity<>(countUser, HttpStatus.OK);
    }
}
