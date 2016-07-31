package com.redhat.chalupa.mobile;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.redhat.chalupa.mobile.api.resources.UserResource;
import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.Datastore;

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
        final Injector injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {}

            @Provides
            public Datastore createDatastore() throws Exception {
                return DatastoreProvider.provide(configuration.getMongo());
            }
        });

        // register resources
        environment.jersey().register(injector.getInstance(UserResource.class));
    }
}
