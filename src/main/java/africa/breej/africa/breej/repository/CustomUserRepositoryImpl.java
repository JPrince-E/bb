package africa.breej.africa.breej.repository;

import africa.breej.africa.breej.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class CustomUserRepositoryImpl implements CustomUserRepository{
    private MongoTemplate mongoTemplate;

    public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<User> findUserByFilters(HashMap<String, Object> filters, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        filters.put("deleted", false);
        Query query = new Query();
        for (String filter : filters.keySet()) {
            if (filters.get(filter) instanceof Collection)
                query.addCriteria(Criteria.where(filter).in((Collection<?>) filters.get(filter)));
            else
                query.addCriteria(Criteria.where(filter).is(filters.get(filter)));
        }

        //apply date filters
        if (from != null && to != null) {
            query.addCriteria(Criteria.where("timeCreated").lte(to).gte(from));
        } else if (from != null) {
            query.addCriteria(Criteria.where("timeCreated").gte(from));
        } else if (to != null) {
            query.addCriteria(Criteria.where("timeCreated").lte(to));
        }
        query.with(pageable);

        List<User> userList = mongoTemplate.find(query, User.class);
        return PageableExecutionUtils.getPage(userList, pageable,
                () -> mongoTemplate.count(query, User.class));
    }
}
