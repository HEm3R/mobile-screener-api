package com.redhat.chalupa.mobile.api.dtos;

import lombok.Data;

@Data
public abstract class UserDto {

    private String gender;
    private NameDto name;
    private LocationDto location;
    private String email;
    private String username;
    private Long registered;
    private Long dob;
    private String phone;
    private String cell;
    private String PPS;
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
