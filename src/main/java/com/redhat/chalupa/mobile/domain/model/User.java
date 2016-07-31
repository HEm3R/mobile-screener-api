package com.redhat.chalupa.mobile.domain.model;

import com.redhat.chalupa.mobile.domain.converters.IntegerConvertible;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "User", noClassnameStored = true)
@Data
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;

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

    @Builder
    public User(
            ObjectId id, Gender gender, Name name, Location location, String email, String username, String password,
            String salt, String md5, String sha1, String sha256, Long registered, Long dob, String phone, String cell,
            String PPS, Picture picture) {

        this.id = id;
        this.gender = gender;
        this.name = name;
        this.location = location;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.md5 = md5;
        this.sha1 = sha1;
        this.sha256 = sha256;
        this.registered = registered;
        this.dob = dob;
        this.phone = phone;
        this.cell = cell;
        this.PPS = PPS;
        this.picture = picture;
    }

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
