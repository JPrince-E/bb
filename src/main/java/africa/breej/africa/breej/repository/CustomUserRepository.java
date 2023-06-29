package africa.breej.africa.breej.repository;

import africa.breej.africa.breej.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;

public interface CustomUserRepository {
    Page<User> findUserByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, Pageable pageable);

}
