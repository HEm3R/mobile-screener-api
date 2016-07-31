package com.redhat.chalupa.mobile.mediation;


import com.redhat.chalupa.mobile.api.dtos.UserDto;
import com.redhat.chalupa.mobile.api.dtos.UserViewDto;
import com.redhat.chalupa.mobile.domain.model.User;
import org.junit.Test;

import static com.redhat.chalupa.mobile.domain.model.User.Gender.FEMALE;
import static com.redhat.chalupa.mobile.domain.model.User.Gender.MALE;
import static com.redhat.chalupa.mobile.domain.model.User.builder;
import static com.redhat.chalupa.mobile.mediation.OrikaProducer.mapper;
import static org.assertj.core.api.Assertions.assertThat;

public class OrikaProducerTest {

    @Test
    public void genderToStringConverterShouldBeRegistered() {
        final UserDto dto = new UserViewDto();
        dto.setGender("male");
        assertThat(mapper().map(dto, User.class).getGender()).isEqualTo(MALE);
        dto.setGender("female");
        assertThat(mapper().map(dto, User.class).getGender()).isEqualTo(FEMALE);

        assertThat(mapper().map(builder().gender(MALE).build(), UserViewDto.class).getGender()).isEqualTo("male");
        assertThat(mapper().map(builder().gender(FEMALE).build(), UserViewDto.class).getGender()).isEqualTo("female");
    }
}
