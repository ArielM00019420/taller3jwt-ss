package com.renegz.pnccontroller.controllers;

import com.renegz.pnccontroller.domain.dtos.GeneralResponse;
import com.renegz.pnccontroller.domain.dtos.LoginDTO;
import com.renegz.pnccontroller.domain.dtos.TokenDTO;
import com.renegz.pnccontroller.domain.dtos.UserRegisterDTO;
import com.renegz.pnccontroller.domain.entities.Token;
import com.renegz.pnccontroller.domain.entities.User;
import com.renegz.pnccontroller.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody @Valid LoginDTO info) {
        User user = userService.findByUsernameOrEmail(info.getIdentifier(), info.getIdentifier());

        if(user == null) {
            return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "User not found");
        }

//        if(!userService.checkPassword(user, info.getPassword())) {
//            return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials");
//        }
        try {
            Token token = userService.registerToken(user);
            GeneralResponse response = new GeneralResponse("Login successful", new TokenDTO(token));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid UserRegisterDTO info) {
        User user = userService.findByUsernameOrEmail(info.getUsername(), info.getEmail());

        if(user != null) {
            return GeneralResponse.getResponse(HttpStatus.CONFLICT, "User already exists");
        }

        userService.register(info);

        return GeneralResponse.getResponse(HttpStatus.CREATED, "User registered successfully");
    }
}
