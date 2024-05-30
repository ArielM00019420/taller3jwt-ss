package com.renegz.pnccontroller.services;

import com.renegz.pnccontroller.domain.dtos.UserRegisterDTO;
import com.renegz.pnccontroller.domain.entities.Token;
import com.renegz.pnccontroller.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User findByIdentifier(String identifier);
    User findByUsernameOrEmail(String username, String email);
    void register(UserRegisterDTO userRegisterDTO);
    boolean checkPassword(User user, String password);

    //Token management
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token);
    void cleanTokens(User user) throws Exception;

    //User management
    User findUserAuthenticated();
}
