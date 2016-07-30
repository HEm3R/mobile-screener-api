package com.redhat.chalupa.mobile.dal;

import com.google.inject.Inject;
import com.redhat.chalupa.mobile.domain.model.User;
import lombok.NonNull;
import org.mongodb.morphia.Datastore;

/**
 * Data access object for {@link User}.
 */
public class UserDao {

    private Datastore datastore;

    @Inject
    public UserDao(@NonNull Datastore datastore) {
        this.datastore = datastore;
    }

    public void save(User user) {
        datastore.save(user);
    }
}
