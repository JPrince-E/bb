package africa.breej.africa.breej.controller;

import africa.breej.africa.breej.exception.ConflictException;
import africa.breej.africa.breej.exception.NotAcceptableException;
import africa.breej.africa.breej.exception.NotFoundException;
import africa.breej.africa.breej.model.user.User;
import africa.breej.africa.breej.payload.Response;
import africa.breej.africa.breej.payload.user.UpdateUserPasswordRequest;
import africa.breej.africa.breej.payload.user.UpdateUserProfileRequest;
import africa.breej.africa.breej.security.CurrentUser;
import africa.breej.africa.breej.security.UserPrincipal;
import africa.breej.africa.breej.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
       return userService.fetchUserById(userPrincipal.getId()).get();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Response> updateUserPin(@CurrentUser UserPrincipal userPrincipal, @Validated @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest)
            throws NotAcceptableException, ConflictException, NotFoundException {
        User user = userService.updatePassword(userPrincipal.getId(), updateUserPasswordRequest);
        return ResponseEntity.ok(new Response(true, true, "Password updated successfully", user));
    }

    @PutMapping("/update-profile")
    public User updateUserProfile(@CurrentUser UserPrincipal userPrincipal,@Validated @RequestBody UpdateUserProfileRequest userProfileRequest) {
        return userService.updateUser(userPrincipal.getId(), userProfileRequest);
    }

}
