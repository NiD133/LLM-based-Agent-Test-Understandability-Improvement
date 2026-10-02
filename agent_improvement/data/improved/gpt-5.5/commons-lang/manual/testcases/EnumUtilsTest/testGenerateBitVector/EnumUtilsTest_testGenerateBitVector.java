package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    private enum Enum64 {
        A0,
        A1,
        A2,
        A3,
        A4,
        A5,
        A6,
        A7,
        A8,
        A9,
        A10,
        A11,
        A12,
        A13,
        A14,
        A15,
        A16,
        A17,
        A18,
        A19,
        A20,
        A21,
        A22,
        A23,
        A24,
        A25,
        A26,
        A27,
        A28,
        A29,
        A30,
        A31,
        A32,
        A33,
        A34,
        A35,
        A36,
        A37,
        A38,
        A39,
        A40,
        A41,
        A42,
        A43,
        A44,
        A45,
        A46,
        A47,
        A48,
        A49,
        A50,
        A51,
        A52,
        A53,
        A54,
        A55,
        A56,
        A57,
        A58,
        A59,
        A60,
        A61,
        A62,
        A63
    }

    @Test
    void testGenerateBitVector() {
        assertBitVector(0L, Traffic.class, EnumSet.noneOf(Traffic.class));
        assertBitVector(1L, Traffic.class, EnumSet.of(Traffic.RED));
        assertBitVector(2L, Traffic.class, EnumSet.of(Traffic.AMBER));
        assertBitVector(4L, Traffic.class, EnumSet.of(Traffic.GREEN));
        assertBitVector(3L, Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER));
        assertBitVector(5L, Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN));
        assertBitVector(6L, Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN));
        assertBitVector(7L, Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN));

        assertBitVector(1L << 31, Enum64.class, EnumSet.of(Enum64.A31));
        assertBitVector(1L << 32, Enum64.class, EnumSet.of(Enum64.A32));
        assertBitVector(1L << 63, Enum64.class, EnumSet.of(Enum64.A63));
        assertBitVector(Long.MIN_VALUE, Enum64.class, EnumSet.of(Enum64.A63));
    }

    private <E extends Enum<E>> void assertBitVector(final long expected, final Class<E> enumClass, final EnumSet<E> values) {
        assertEquals(expected, EnumUtils.generateBitVector(enumClass, values));
    }
}
