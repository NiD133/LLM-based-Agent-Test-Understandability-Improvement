package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectorFromArray extends AbstractLangTest {

    private enum Traffic {
        RED, AMBER, GREEN
    }

    private enum Enum64 {
        A00, A01, A02, A03, A04, A05, A06, A07,
        A08, A09, A10, A11, A12, A13, A14, A15,
        A16, A17, A18, A19, A20, A21, A22, A23,
        A24, A25, A26, A27, A28, A29, A30, A31,
        A32, A33, A34, A35, A36, A37, A38, A39,
        A40, A41, A42, A43, A44, A45, A46, A47,
        A48, A49, A50, A51, A52, A53, A54, A55,
        A56, A57, A58, A59, A60, A61, A62, A63
    }

    @Test
    void testGenerateBitVectorFromArray() {
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class));

        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, Traffic.GREEN));

        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.GREEN));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER, Traffic.GREEN));
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN));

        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));

        // Verify high ordinals use long shifts, including the sign bit at ordinal 63.
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, Enum64.A31));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, Enum64.A32));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }
}
