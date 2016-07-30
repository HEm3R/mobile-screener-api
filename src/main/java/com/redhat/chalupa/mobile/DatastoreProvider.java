package com.redhat.chalupa.mobile;

import com.mongodb.MongoClient;
import com.redhat.chalupa.mobile.dal.UserDao;
import com.redhat.chalupa.mobile.domain.converters.IntegerConvertibleEnumConverter;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

@Slf4j
public class DatastoreProvider {

    private static final String MODEL_PACKAGE = "com.redhat.mobile.domain.model";
    private static final String SEED = "/seed.json";

    public static Datastore provide(MongoConfiguration config) {
        final Morphia morphia = new Morphia();
        morphia.mapPackage(MODEL_PACKAGE);

        // TODO: create dynamic lookup
        morphia.getMapper().getConverters().addConverter(IntegerConvertibleEnumConverter.class);

        log.info("action=create_mongo_client status=START host={} port={}", config.getHost(), config.getPort());
        final MongoClient client = new MongoClient(config.getHost(), config.getPort());
        log.info("action=create_mongo_client status=FINISH host={} port={}", config.getHost(), config.getPort());

        log.info("action=create_mongo_datastore status=START db={}", config.getDb());
        final Datastore datastore = morphia.createDatastore(client, config.getDb());
        log.info("action=create_mongo_datastore status=FINISH db={}", config.getDb());

        log.info("action=ensuring_indexes status=START");
        datastore.ensureIndexes();

        if (config.isSeed()) {
            new MongoDataSeeder(new UserDao(datastore)).seed(MongoDataSeeder.class.getResourceAsStream(SEED));
        }

        return datastore;
    }
}
