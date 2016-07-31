package com.redhat.chalupa.mobile.api.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserViewDto extends UserDto {

    private String id;
}
