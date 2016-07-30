package com.redhat.chalupa.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

import static java.time.ZoneOffset.UTC;

/**
 * Application bootstrap.
 */
@Slf4j
public class MobileScreenerApi extends Application<MobileScreenerApiConfiguration> {

    public static void main(String[] args) throws Exception {
        new MobileScreenerApi().run(args);
    }

    @Override
    public void initialize(final Bootstrap<MobileScreenerApiConfiguration> bootstrap) {
        bootstrap.addBundle(new TemplateConfigBundle());
    }

    @Override
    public void run(MobileScreenerApiConfiguration configuration, Environment environment) throws Exception {
        log.info("[bootstrap] Application timezone is UTC");
        TimeZone.setDefault(TimeZone.getTimeZone(UTC));

        log.info("[bootstrap] Create Guice injector");
        Injector injector = Guice.createInjector();

        if (configuration.isProcessDBSeed()) {
            log.info("seed DB");
        }
    }
}
