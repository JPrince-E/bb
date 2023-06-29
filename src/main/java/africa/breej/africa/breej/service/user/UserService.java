package africa.breej.africa.breej.service.user;

import africa.breej.africa.breej.model.user.User;
import africa.breej.africa.breej.payload.auth.SignUpRequest;
import africa.breej.africa.breej.payload.user.UpdateUserPasswordRequest;
import africa.breej.africa.breej.payload.user.UpdateUserProfileRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User createUser(SignUpRequest users);

    Optional<User> fetchUserById(String id);

    List<User> fetchAllUsers();

    User updatePassword(String id, UpdateUserPasswordRequest updateUserPinRequest);

    User updateUser(String id, UpdateUserProfileRequest userProfileRequest);
}
