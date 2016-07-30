package com.redhat.chalupa.mobile;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class MongoConfiguration {

    @NotEmpty
    private String host;

    @Min(1) @Max(65535)
    private int port;

    @NotEmpty
    private String db;

    private boolean seed;
}
