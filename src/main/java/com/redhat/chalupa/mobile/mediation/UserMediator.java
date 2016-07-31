package com.redhat.chalupa.mobile.mediation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.redhat.chalupa.mobile.api.dtos.UserUpdateDto;
import com.redhat.chalupa.mobile.api.dtos.UserViewDto;
import com.redhat.chalupa.mobile.api.params.PaginationFilter;
import com.redhat.chalupa.mobile.dal.UserDao;
import com.redhat.chalupa.mobile.domain.model.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;

import static com.redhat.chalupa.mobile.mediation.OrikaProducer.mapper;

/**
 * Mediation layer between API and DAO for.
 */
@Slf4j
@Singleton
public class UserMediator {

    private UserDao dao;

    @Inject
    public UserMediator(UserDao dao) {
        this.dao = dao;
    }

    public EntityList<UserViewDto> getAll(@NonNull PaginationFilter filter) {
        final List<User> users = dao.find(filter.getLimit(), filter.getOffset());
        final long totalCount = dao.count();
        return new EntityList<>(mapper().mapAsList(users, UserViewDto.class), totalCount);
    }

    public UserViewDto get(@NonNull String id) {
        final ObjectId objectId = toObjectId(id);
        if (objectId == null) {
            throw new NotFoundException();
        }

        User user = dao.find(objectId);
        if (user == null) {
            throw new NotFoundException();
        }
        return mapper().map(user, UserViewDto.class);
    }

    public void update(@NonNull String id, @NonNull UserUpdateDto userUpdateDto) {
        final ObjectId objectId = toObjectId(id);
        if (objectId == null) {
            throw new BadRequestException();
        }

        User user = mapper().map(userUpdateDto, User.class);
        user.setId(objectId);
        dao.save(user);
    }

    public UserViewDto create(@NonNull UserUpdateDto userUpdateDto) {
        User user = mapper().map(userUpdateDto, User.class);
        dao.save(user);
        return mapper().map(user, UserViewDto.class);
    }

    public void delete(String id) {
        final ObjectId objectId = toObjectId(id);
        if (objectId != null) {
            dao.remove(new ObjectId(id));
        }
    }

    private ObjectId toObjectId(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            log.debug("ID={} is not valid ObjectId representation", id, e);
        }
        return null;
    }
}
