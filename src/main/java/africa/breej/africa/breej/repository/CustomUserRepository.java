package africa.breej.africa.breej.repository;

import africa.breej.africa.breej.model.auth.UserReport;
import africa.breej.africa.breej.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface CustomUserRepository {
    Page<User> findUserByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, Pageable pageable);

    List<UserReport> userOverviewAggregation(LocalDateTime from, LocalDateTime to);
}

