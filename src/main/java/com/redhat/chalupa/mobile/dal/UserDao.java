package com.redhat.chalupa.mobile.dal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.redhat.chalupa.mobile.domain.model.User;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Data access object for {@link User}.
 */
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

    public List<User> find() {
        return datastore.find(User.class).asList();
    }

    public long count() {
        return datastore.find(User.class).countAll();
    }

    public void remove(ObjectId id) {
        datastore.delete(datastore.createQuery(User.class).field(ID_FIELD).equal(id));
    }
}
