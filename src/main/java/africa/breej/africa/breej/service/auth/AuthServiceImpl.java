package africa.breej.africa.breej.service.auth;

import africa.breej.africa.breej.model.user.User;
import africa.breej.africa.breej.model.auth.AuthProvider;
import africa.breej.africa.breej.payload.auth.AuthResponse;
import africa.breej.africa.breej.payload.auth.LoginRequest;
import africa.breej.africa.breej.payload.auth.SignUpRequest;
import africa.breej.africa.breej.payload.user.UserResponse;
import africa.breej.africa.breej.repository.UserRepository;
import africa.breej.africa.breej.security.TokenProvider;
import africa.breej.africa.breej.util.StringUtil;
import com.amazonaws.services.alexaforbusiness.model.UnauthorizedException;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public URI registerUser(SignUpRequest signUpRequest) {

        if(!StringUtil.isBlank(signUpRequest.getEmail())) {
            if(userRepository.existsByEmailAndDeleted(signUpRequest.getEmail(),false)) {
                LOGGER.info(String.format("Email address already in use: %s", signUpRequest.getEmail()));
                throw new NotAcceptableStatusException("Email address already in use.");
            }
        }

        if(userRepository.existsByPhoneNumberAndDeleted(signUpRequest.getPhoneNumber(), false)) {
            LOGGER.info(String.format("Phone number already in use: %s", signUpRequest.getPhoneNumber()));
            throw new NotAcceptableStatusException("Phone number already in use.");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setProvider(AuthProvider.LOCAL);
        user.setTimeCreated(LocalDateTime.now());

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return location;
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication=null;
        if(loginRequest.getEmail()!=null && !loginRequest.getEmail().isEmpty())  {
            if(!userRepository.existsByEmailAndDeleted(loginRequest.getEmail(),false)) {
                throw new UnauthorizedException("Unauthenticated.");
            }
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        }

        if(loginRequest.getPhoneNumber()!=null && !loginRequest.getPhoneNumber().isEmpty()){
            if(!userRepository.existsByPhoneNumber(loginRequest.getPhoneNumber())) {
                throw new UnauthorizedException("Unauthenticated.");
            }
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getPhoneNumber(),
                            loginRequest.getPassword()
                    )
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber()).get();
        UserResponse userResponse =  getUserResponseFromUser(user);
        updateLastLogin(user);

        String token = passwordEncoder.encode(user.getEmail());
        return new AuthResponse(token, userResponse);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserResponse getUserResponseFromUser(User user) {
        UserResponse userResponse = new UserResponse();
        try {
            BeanUtils.copyProperties(userResponse, user);
        } catch (Exception exception) {
            LOGGER.error("Error copying user properties");
        }
        return userResponse;
    }

    private void updateLastLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
