package com.redhat.chalupa.mobile.dal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.redhat.chalupa.mobile.domain.model.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Data access object for {@link User}.
 */
@Slf4j
@Singleton
public class UserDao {

    private static final String ID_FIELD = "_id";

    private final Datastore datastore;

    @Inject
    public UserDao(@NonNull Datastore datastore) {
        this.datastore = datastore;
    }

    public void save(User user) {
        datastore.save(user);
    }

    public User find(Object id) {
        return datastore.find(User.class, ID_FIELD, id).get();
    }

    public List<User> find(Integer limit, Integer offset, String orderBy) {
        final Query<User> query = datastore.find(User.class);
        if (limit != null) {
            query.limit(limit);
        }
        if (offset != null) {
            query.offset(offset);
        }
        if (orderBy != null) {
            query.order(orderBy);
        }

        log.info("action=find-users status=START limit={} offset={} orderBy={}", limit, offset, orderBy);
        final List<User> users = query.asList();
        log.info("action=find-users status=FINISH limit={} offset={} orderBy={}", limit, offset, orderBy);
        return users;
    }

    public long count() {
        return datastore.find(User.class).countAll();
    }

    public void remove(ObjectId id) {
        datastore.delete(datastore.createQuery(User.class).field(ID_FIELD).equal(id));
    }
}
