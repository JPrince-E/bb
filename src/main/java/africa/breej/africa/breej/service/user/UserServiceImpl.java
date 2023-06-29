package africa.breej.africa.breej.service.user;

import africa.breej.africa.breej.model.User;
import africa.breej.africa.breej.payload.auth.SignUpRequest;
import africa.breej.africa.breej.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(SignUpRequest users) {

        User user = new User();

        user.setFirstName(users.getFirstName());
        user.setLastName(users.getLastName());
        user.setEmail(users.getEmail());

        user.setPassword(passwordEncoder.encode(users.getPassword()));

        return userRepository.save(user);
}

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> fetchUserById(String id) {
        Optional<User> user = userRepository.findById(id);
         return user;

    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

}
