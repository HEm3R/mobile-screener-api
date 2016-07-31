package com.redhat.chalupa.mobile.mediation;

import com.redhat.chalupa.mobile.api.dtos.UserUpdateDto;
import com.redhat.chalupa.mobile.api.dtos.UserViewDto;
import com.redhat.chalupa.mobile.api.params.PaginationFilter;
import com.redhat.chalupa.mobile.dal.UserDao;
import com.redhat.chalupa.mobile.domain.model.User;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Arrays;

import static com.redhat.chalupa.mobile.mediation.OrikaProducer.mapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMediatorTest {

    @Mock
    private UserDao dao;

    private UserMediator mediator;

    @Before
    public void setUp() {
        mediator = new UserMediator(dao);
    }

    @Test
    public void testGetAll() {
        final PaginationFilter filter = new PaginationFilter();
        filter.setLimit(5);
        filter.setOffset(10);
        final User user = User.builder().username("username").build();
        final long totalCount = 16L;
        when(dao.find(filter.getLimit(), filter.getOffset())).thenReturn(Arrays.asList(user));
        when(dao.count()).thenReturn(totalCount);


        final EntityList<UserViewDto> views = mediator.getAll(filter);
        assertThat(views.getEntities()).containsExactly(mapper().map(user, UserViewDto.class));
        assertThat(views.getTotalCount()).isEqualTo(totalCount);
    }

    @Test
    public void testGet() {
        final ObjectId id = new ObjectId();
        final User user = User.builder().id(id).build();
        when(dao.find(id)).thenReturn(user);

        assertThat(mediator.get(id.toString())).isEqualTo(mapper().map(user, UserViewDto.class));
    }

    @Test(expected = NotFoundException.class)
    public void getShouldThrowNotFoundIfNotFound() {
        when(dao.find(any())).thenReturn(null);
        assertThat(mediator.get(new ObjectId().toString())).isNull();
    }

    @Test(expected = NotFoundException.class)
    public void getShouldThrowNotFoundIfNotValidObjectId() {
        mediator.get("1");
    }

    @Test
    public void testCreate() {
        final UserUpdateDto update = new UserUpdateDto();
        update.setUsername("username");
        final User user = mapper().map(update, User.class);

        UserViewDto view = mediator.create(update);

        verify(dao).save(user);
        assertThat(view).isEqualTo(mapper().map(user, UserViewDto.class));
    }

    @Test
    public void testUpdate() {
        final ObjectId id = new ObjectId();
        final UserUpdateDto update = new UserUpdateDto();
        update.setUsername("username");
        final User user = mapper().map(update, User.class);
        user.setId(id);

        mediator.update(id.toString(), update);

        verify(dao).save(user);
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowBadRequestIfNotValidObjectId() {
        mediator.update("1", new UserUpdateDto());
    }

    @Test
    public void testDelete() {
        final ObjectId id = new ObjectId();
        mediator.delete(id.toString());

        verify(dao).remove(id);
    }
}
