package com.redhat.chalupa.mobile.dal;

import com.redhat.chalupa.mobile.domain.model.User;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Datastore datastore;

    private UserDao dao;

    @Before
    public void setUp() {
        dao = new UserDao(datastore);
    }

    @Test
    public void testSave() {
        final User user = User.builder().username("username").build();
        dao.save(user);

        verify(datastore).save(user);
    }

    @Test
    public void testFindAll() {
        final User user = User.builder().username("username").build();
        when(datastore.find(User.class).asList()).thenReturn(Arrays.asList(user));
        assertThat(dao.find(0, 0)).containsExactly(user);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindAllWithFilter() {
        final int limit = 15;
        final int offset = 2;
        final Query<User> query = mock(Query.class);
        when(datastore.find(User.class)).thenReturn(query);

        assertThat(dao.find(limit, offset));

        verify(query).limit(limit);
        verify(query).offset(offset);
    }

    @Test
    public void testFind() {
        final ObjectId id = new ObjectId();
        final User user = User.builder().username("username").build();
        when(datastore.find(User.class, "_id", id).get()).thenReturn(user);

        assertThat(dao.find(id)).isEqualTo(user);
    }

    @Test
    public void testCount() {
        final long count = 10L;
        when(datastore.find(User.class).countAll()).thenReturn(count);
        assertThat(dao.count()).isEqualTo(count);
    }

    @Test
    public void testRemove() {
        final ObjectId id = new ObjectId();
        final Query<User> query = datastore.createQuery(User.class).field("_id").equal(id);
        dao.remove(id);

        verify(datastore).delete(query);
    }
}
