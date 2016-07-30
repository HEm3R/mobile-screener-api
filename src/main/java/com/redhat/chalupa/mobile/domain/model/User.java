package com.redhat.chalupa.mobile.domain.model;

import com.redhat.chalupa.mobile.domain.converters.IntegerConvertible;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Entity;

@Data
@Builder
@Entity(value = "User", noClassnameStored = true)
public class User {

    private Gender gender;
    private Name name;
    private Location location;
    private String email;
    private String username;
    private String password; // TODO: question - password is here as plain text?
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;
    private Long registered; // TODO: convert to LocalDateTime
    private Long dob;
    private String phone;
    private String cell;
    private String PPS;
    private Picture picture;

    @AllArgsConstructor
    public enum Gender implements IntegerConvertible {

        MALE(0), FEMALE(1);

        @Getter private int value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {

        private String title;
        private String first;
        private String last;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {

        private String street;
        private String city;
        private String state;
        private int zip;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Picture {

        private String large;
        private String medium;
        private String thumbnail;
    }
}
