package com.renegz.pnccontroller.services.implementations;

import com.renegz.pnccontroller.domain.dtos.UserRegisterDTO;
import com.renegz.pnccontroller.domain.entities.Token;
import com.renegz.pnccontroller.domain.entities.User;
import com.renegz.pnccontroller.repositories.TokenRepository;
import com.renegz.pnccontroller.repositories.UserRepository;
import com.renegz.pnccontroller.services.UserService;
import com.renegz.pnccontroller.utils.JWTTools;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private TokenRepository tokenRepository;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByIdentifier(String identifier) {
        return userRepository.findByUsernameOrEmail(identifier, identifier).orElse(null);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void register(UserRegisterDTO UserInfo) {
        User user = new User();

        user.setUsername(UserInfo.getUsername());
        user.setEmail(UserInfo.getEmail());
        user.setPassword(UserInfo.getPassword());

        userRepository.save(user);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(User user) throws Exception {
        cleanTokens(user);

        String tokenString = jwtTools.generateToken(user);
        Token token = new Token(tokenString, user);

        tokenRepository.save(token);

        return token;
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanTokens(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

            tokens.stream()
                    .filter(tk -> tk.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new Exception());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(User user) throws Exception {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        tokens.forEach(token -> {
            if (!jwtTools.verifyToken(token.getContent())) {
                token.setActive(false);
                tokenRepository.save(token);
            }
        });
    }
//Implementing the method to find the authenticated user
    @Override
    public User findUserAuthenticated() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsernameOrEmail(username, username).orElse(null);
    }

}
