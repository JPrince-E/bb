package africa.breej.africa.breej.repository;

import africa.breej.africa.breej.model.auth.UserReport;
import africa.breej.africa.breej.model.user.User;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonNull;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Repository
@Slf4j
public class CustomUserRepositoryImpl implements CustomUserRepository{
    private MongoTemplate mongoTemplate;

    public static final String PROJECT_BREEJ_DB_USER = "project_breej_db_user";

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
    @Override
    public List<UserReport> userOverviewAggregation(LocalDateTime from, LocalDateTime to) {
        List<UserReport> userReportList = new ArrayList<>();
        MongoCollection<Document> collection = mongoTemplate.getDb().getCollection(PROJECT_BREEJ_DB_USER);

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$match",
                        new Document("timeCreated",
                                new Document("$gte",
                                        Date.from(from.atZone(ZoneId.systemDefault()).toInstant()))
                                        .append("$lt",
                                                Date.from(to.atZone(ZoneId.systemDefault()).toInstant())))),
                new Document("$group",
                        new Document("_id",
                                new BsonNull())
                                .append("count",
                                        new Document("$sum", 1L)))));
        Spliterator<Document> documentSpliterator = result.spliterator();
        documentSpliterator.forEachRemaining(document -> {
            log.info("doc json: " + document.toJson());
            UserReport userReport = new UserReport();
            userReport.setTotalCount(Long.parseLong(document.get("count").toString()));
            userReportList.add(userReport);
        });
        return userReportList;
    }
}

