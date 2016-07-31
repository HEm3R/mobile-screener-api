package com.redhat.chalupa.mobile.api.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserUpdateDto extends UserDto {

    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;
}
