package com.redhat.chalupa.mobile.domain.converters;

import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * Converter for Enum of type {@link IntegerConvertible} -> Integer and vice versa conversion.
 *
 * <p>
 *    Enums are stored to MongoDB as integers and not as strings.
 * </p>
 */
public class IntegerConvertibleEnumConverter extends TypeConverter {

    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
        if (fromDBObject instanceof Integer == false) {
            return null;
        }

        int value = (Integer) fromDBObject;
        Optional<IntegerConvertible> convertible = stream(targetClass.getEnumConstants())
                .map(c -> (IntegerConvertible) c)
                .filter(c -> c.getValue() == value)
                .findFirst();

        return convertible.isPresent() ? convertible.get() : null;
    }

    @Override
    public Object encode(final Object value, final MappedField optionalExtraInfo) {
        return value == null ? value : ((IntegerConvertible) value).getValue();
    }

    @Override
    protected boolean isSupported(final Class c, final MappedField optionalExtraInfo) {
        return IntegerConvertible.class.isAssignableFrom(c);
    }
}
