package com.redhat.chalupa.mobile.api.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public abstract class UserDto {

    @Pattern(regexp = "(male|female)")
    private String gender;

    @NotNull
    private NameDto name;

    @NotNull
    private LocationDto location;

    @Email
    private String email;

    @NotEmpty
    private String username;

    @NotNull
    private Long registered;

    @NotNull
    private Long dob;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String cell;

    @NotEmpty
    private String PPS;

    @NotNull
    private PictureDto picture;

    @Data
    public static class NameDto {

        private String title;
        private String first;
        private String last;
    }

    @Data
    public static class LocationDto {

        private String street;
        private String city;
        private String state;
        private int zip;
    }

    @Data
    public static class PictureDto {

        private String large;
        private String medium;
        private String thumbnail;
    }
}
