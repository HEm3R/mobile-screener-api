package com.redhat.chalupa.mobile.mediation;

import com.redhat.chalupa.mobile.domain.model.User.Gender;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

/**
 * Orika mapper configuration.
 */
public class OrikaProducer {

    private static final MapperFacade MAPPER;

    private static class GenderConverter extends BidirectionalConverter<Gender, String> {

        @Override
        public String convertTo(Gender source, Type<String> destinationType) {
            return source.toString().toLowerCase();
        }

        @Override
        public Gender convertFrom(String source, Type<Gender> destinationType) {
            return Gender.valueOf(source.toUpperCase());
        }
    }

    static {
        final MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.getConverterFactory().registerConverter(new GenderConverter());

        MAPPER = factory.getMapperFacade();
    }

    private OrikaProducer() { // do not initialize
    }

    /**
     * @return configured Orika mapper
     */
    public static MapperFacade mapper() {
        return MAPPER;
    }
}
