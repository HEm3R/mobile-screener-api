package com.redhat.chalupa.mobile.api.resources;

import com.redhat.chalupa.mobile.api.dtos.UserUpdateDto;
import com.redhat.chalupa.mobile.api.dtos.UserViewDto;
import com.redhat.chalupa.mobile.api.params.FilterableSortablePaginationFilter;
import com.redhat.chalupa.mobile.api.params.PaginationFilter;
import com.redhat.chalupa.mobile.api.params.SortablePaginationFilter;
import com.redhat.chalupa.mobile.mediation.EntityList;
import com.redhat.chalupa.mobile.mediation.UserMediator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

@Slf4j
@Path(UserResource.PATH)
@Produces(APPLICATION_JSON)
public class UserResource {

    /**
     * Path of user resource.
     */
    public static final String PATH = "/users";

    private final UserMediator mediator;

    @Inject
    public UserResource(@NonNull UserMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Gets all users.
     *
     * @return found users
     */
    @GET
    public Response getAll(@Valid @BeanParam FilterableSortablePaginationFilter filter) {
        final EntityList<UserViewDto> entityList = mediator.getAll(filter);
        // TODO: move the extraction to JAX-RS response filter
        return ok().header("X-TotalCount", entityList.getTotalCount()).entity(entityList.getEntities()).build();
    }

    /**
     * Gets a user by the provided ID.
     *
     * @param id ID of user to be retrieved
     * @return found user if exists
     */
    @Path("/{id}")
    @GET
    public Response get(@PathParam("id") String id) {
        final UserViewDto dto = mediator.get(id);
        return dto != null ? ok().entity(dto).build() : Response.status(NOT_FOUND).build();
    }

    /**
     * Creates a user.
     *
     * @param userUpdateDto data
     * @return created user
     */
    @POST
    @Consumes(APPLICATION_JSON)
    public Response create(@Valid UserUpdateDto userUpdateDto) {
        final UserViewDto dto = mediator.create(userUpdateDto);
        return Response.status(CREATED).header("Location", PATH + "/" + dto.getId()).entity(dto).build();
    }

    /**
     * Updates a user by the provided ID.
     *
     * @param id ID of user
     * @param userUpdateDto data
     * @return operation confirmation
     */
    @Path("/{id}")
    @PUT
    @Consumes(APPLICATION_JSON)
    public Response update(@PathParam("id") String id, @Valid UserUpdateDto userUpdateDto) {
        mediator.update(id, userUpdateDto);
        return noContent().build();
    }

    /**
     * Deletes a user by the provided ID.
     *
     * @param id ID of user
     * @return operation confirmation
     */
    @Path("/{id}")
    @DELETE
    public Response remove(@PathParam("id") String id) {
        mediator.delete(id);
        return noContent().build();
    }
}
