package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVector extends AbstractLangTest {

    @Test
    void testProcessBitVector() {
        assertTrafficSignalBitVectors();
        assertEnum64BoundaryBitVectors();
    }

    private void assertTrafficSignalBitVectors() {
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVector(Traffic.class, 0L));
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVector(Traffic.class, 1L));
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 2L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 3L));
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 4L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 5L));
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 6L));
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 7L));
    }

    private void assertEnum64BoundaryBitVectors() {
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVector(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVector(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, Long.MIN_VALUE));
    }

    private enum Traffic {
        RED,
        AMBER,
        GREEN
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
}
