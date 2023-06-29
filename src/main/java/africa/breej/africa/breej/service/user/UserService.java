package africa.breej.africa.breej.service.user;

import africa.breej.africa.breej.model.User;
import africa.breej.africa.breej.payload.auth.SignUpRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User createUser(SignUpRequest users);

    Optional<User> fetchUserById(String id);

    List<User> fetchAllUsers();
}
