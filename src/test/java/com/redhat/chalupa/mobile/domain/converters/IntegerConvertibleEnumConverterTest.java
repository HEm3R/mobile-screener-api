package com.redhat.chalupa.mobile.domain.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerConvertibleEnumConverterTest {

    private static final IntegerConvertibleEnumConverter CONVERTER = new IntegerConvertibleEnumConverter();

    @Test
    public void convertibleShouldBeSupported() {
        assertThat(CONVERTER.isSupported(Convertible.class, null)).isTrue();
    }

    @Test
    public void basicEnumShouldNotBeSupported() {
        assertThat(CONVERTER.isSupported(BasicEnum.class, null)).isFalse();
    }

    @Test
    public void testEncoding() {
        assertThat(CONVERTER.encode(Convertible.A, null)).isEqualTo(Convertible.A.value);
        assertThat(CONVERTER.encode(Convertible.B, null)).isEqualTo(Convertible.B.value);
    }

    @Test
    public void testDecoding() {
        assertThat(CONVERTER.decode(Convertible.class, Convertible.A.value, null)).isEqualTo(Convertible.A);
        assertThat(CONVERTER.decode(Convertible.class, Convertible.B.value, null)).isEqualTo(Convertible.B);
    }

    @Test
    public void decodedValueShouldBeNullForUnmappedValue() {
        assertThat(CONVERTER.decode(Convertible.class, 100, null)).isNull();
    }

    @AllArgsConstructor
    private enum Convertible implements IntegerConvertible {
        A(5), B(3);

        @Getter private int value;
    }

    private enum BasicEnum {
    }
}
