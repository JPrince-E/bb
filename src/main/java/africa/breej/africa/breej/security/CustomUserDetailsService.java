package africa.breej.africa.breej.security;

import africa.breej.africa.breej.exception.ResourceNotFoundException;
import africa.breej.africa.breej.model.User;
import africa.breej.africa.breej.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if(!user.isPresent()){
            User userByPhone =  userRepository.findByPhoneNumber(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with email : " + username)
                    );
            return UserPrincipal.create(userByPhone);
        }

        return UserPrincipal.create(user.get());
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
