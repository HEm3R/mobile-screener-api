package com.redhat.chalupa.mobile;

import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Application configuration.
 */
@Data
public class MobileScreenerApiConfiguration extends Configuration {

    @Valid
    @NotNull
    private MongoConfiguration mongo = new MongoConfiguration();
}
