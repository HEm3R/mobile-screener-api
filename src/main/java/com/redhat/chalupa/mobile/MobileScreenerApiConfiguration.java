package com.redhat.chalupa.mobile;

import io.dropwizard.Configuration;
import lombok.Data;

/**
 * Application configuration.
 */
@Data
public class MobileScreenerApiConfiguration extends Configuration {

    /**
     * @return true if the DB should be initialized by default seed.
     */
    private boolean processDBSeed;
}
