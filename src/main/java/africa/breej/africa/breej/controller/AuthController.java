package africa.breej.africa.breej.controller;

import africa.breej.africa.breej.payload.Response;
import africa.breej.africa.breej.payload.auth.AuthResponse;
import africa.breej.africa.breej.payload.auth.LoginRequest;
import africa.breej.africa.breej.payload.auth.SignUpRequest;
import africa.breej.africa.breej.security.TokenProvider;
import africa.breej.africa.breej.service.auth.AuthService;
import africa.breej.africa.breej.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    AuthService authService;

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
//
//        if (authService.findByEmail(signUpRequest.getEmail()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
//        }

        URI location = authService.registerUser(signUpRequest);

        LoginRequest loginRequest = new LoginRequest();
        if(!StringUtil.isBlank(loginRequest.getEmail()))
            loginRequest.setEmail(signUpRequest.getEmail());
        loginRequest.setPhoneNumber(signUpRequest.getPhoneNumber());
        loginRequest.setPassword(signUpRequest.getPassword());
        AuthResponse authResponse = authService.authenticateUser(loginRequest);

        return ResponseEntity.created(location)
                .body(new Response(true, true,"User registered successfully", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(authResponse);
    }
}
