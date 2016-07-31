package com.redhat.chalupa.mobile.api.resources;

import com.redhat.chalupa.mobile.api.dtos.UserUpdateDto;
import com.redhat.chalupa.mobile.api.dtos.UserViewDto;
import com.redhat.chalupa.mobile.mediation.EntityList;
import com.redhat.chalupa.mobile.mediation.UserMediator;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserMediator mediator;

    private UserResource resource;

    @Before
    public void setUp() {
        resource = new UserResource(mediator);
    }

    @Test
    public void testGetAll() {
        final UserViewDto dto = new UserViewDto();
        dto.setUsername("username");
        final EntityList<UserViewDto> views = new EntityList<>(asList(dto), 1L);
        when(mediator.getAll()).thenReturn(views);

        final Response response = resource.getAll();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        assertThat(response.getEntity()).isEqualTo(views.getEntities());
        assertThat(response.getHeaderString("X-TotalCount")).isEqualTo("1");
    }

    @Test
    public void testGetOne() {
        final String id = new ObjectId().toString();
        final UserViewDto dto = new UserViewDto();
        dto.setUsername("username");
        when(mediator.get(id)).thenReturn(dto);

        final Response response = resource.get(id);
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        assertThat(response.getEntity()).isEqualTo(dto);
    }

    @Test
    public void testCreate() {
        final String id = new ObjectId().toString();
        final UserUpdateDto dto = new UserUpdateDto();
        dto.setUsername("username");
        final UserViewDto view = new UserViewDto();
        view.setUsername(dto.getUsername());
        view.setId(id);

        when(mediator.create(dto)).thenReturn(view);

        final Response response = resource.create(dto);
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.CREATED);
        assertThat(response.getHeaderString("Location")).isEqualTo(UserResource.PATH + "/" + view.getId());
    }

    @Test
    public void testUpdate() {
        final String id = new ObjectId().toString();
        final UserUpdateDto dto = new UserUpdateDto();
        dto.setUsername("username");

        final Response response = resource.update(id, dto);
        verify(mediator).update(id, dto);
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);
    }

    @Test
    public void testDelete() {
        final String id = new ObjectId().toString();
        final Response response = resource.remove(id);
        verify(mediator).delete(id);
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);
    }
}
