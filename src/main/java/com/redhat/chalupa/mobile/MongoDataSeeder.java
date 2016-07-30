package com.redhat.chalupa.mobile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.chalupa.mobile.dal.UserDao;
import com.redhat.chalupa.mobile.domain.model.User;
import com.redhat.chalupa.mobile.domain.model.User.Gender;
import com.redhat.chalupa.mobile.domain.model.User.Location;
import com.redhat.chalupa.mobile.domain.model.User.Name;
import com.redhat.chalupa.mobile.domain.model.User.Picture;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * MongoDB data seeder.
 */
@Slf4j
@AllArgsConstructor
public class MongoDataSeeder {

    @NonNull private UserDao userDao;

    private static Gender parseGender(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }

    private static Name parseName(@NonNull JsonNode name) {
        return new Name(text(name, "title"), text(name, "first"), text(name, "last"));
    }

    private static Location parseLocation(@NonNull JsonNode location) {
        return new Location(text(location, "street"), text(location, "city"), text(location, "state"), location.get("zip").asInt());
    }

    private static Picture parsePicture(@NonNull JsonNode picture) {
        return new Picture(text(picture, "large"), text(picture, "medium"), text(picture, "thumbnail"));
    }

    private static String text(JsonNode node, String field) {
        return node.get(field).asText();
    }

    /**
     * Parses the provided seed and saves all parsed {@link User} to DB.
     *
     * @param seed input stream of DB seed
     * @throws SeedParsingException in case of parsing error of the provided seed
     */
    public void seed(InputStream seed) {
        ObjectMapper mapper = new ObjectMapper();
        final JsonNode root;
        try {
            log.info("action=read-db-seed status=START");
            root = mapper.readTree(seed);
            log.info("action=read-db-seed status=FINISH");
        } catch (IOException e) {
            log.info("action=read-db-seed status=ERROR", e);
            throw new SeedParsingException(e);
        }
        root.get("results").forEach(node -> insertUser(node.get("user")));
    }

    private void insertUser(@NonNull JsonNode user) {
        log.debug("action=parse-user status=START node={}", user);
        final User entity = User.builder()
                .gender(parseGender(user.get("gender").asText()))
                .name(parseName(user.get("name")))
                .location(parseLocation(user.get("location")))
                .email(text(user, "email"))
                .username(text(user, "username"))
                .password(text(user, "password"))
                .salt(text(user, "salt"))
                .md5(text(user, "md5"))
                .sha1(text(user, "sha1"))
                .sha256(text(user, "sha256"))
                .registered(user.get("registered").asLong())
                .dob(user.get("dob").asLong())
                .phone(text(user, "phone"))
                .cell(text(user, "cell"))
                .PPS(text(user, "PPS"))
                .picture(parsePicture(user.get("picture")))
                .build();
        log.debug("action=parse-user status=FINISH node={} user={}", user, entity);

        userDao.save(entity);
    }

    /**
     * Signals an error with DB seed parsing.
     */
    public static class SeedParsingException extends RuntimeException {

        SeedParsingException(Throwable throwable) {
            super(throwable);
        }
    }
}
