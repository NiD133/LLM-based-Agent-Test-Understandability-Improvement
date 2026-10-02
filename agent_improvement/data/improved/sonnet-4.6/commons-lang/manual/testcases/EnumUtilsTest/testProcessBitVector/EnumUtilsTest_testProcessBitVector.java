package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVector extends AbstractLangTest {

    // Traffic ordinals: RED=0, AMBER=1, GREEN=2
    // Corresponding bit positions: RED=bit0 (1L), AMBER=bit1 (2L), GREEN=bit2 (4L)

    @Test
    void testProcessBitVector_zeroBitVector_returnsEmptySet() {
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVector(Traffic.class, 0L));
    }

    @Test
    void testProcessBitVector_singleBit_returnsSingletonSet() {
        assertEquals(EnumSet.of(Traffic.RED),   EnumUtils.processBitVector(Traffic.class, 1L)); // bit 0 → RED
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 2L)); // bit 1 → AMBER
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 4L)); // bit 2 → GREEN
    }

    @Test
    void testProcessBitVector_multipleBits_returnsMatchingSet() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER),                EnumUtils.processBitVector(Traffic.class, 3L)); // bits 0+1
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN),                EnumUtils.processBitVector(Traffic.class, 5L)); // bits 0+2
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN),              EnumUtils.processBitVector(Traffic.class, 6L)); // bits 1+2
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 7L)); // bits 0+1+2
    }

    @Test
    void testProcessBitVector_highOrdinals_noIntLongConversionIssue() {
        // Enum64 has 64 constants A0–A63; shifts beyond bit 31 must use long arithmetic
        // to avoid int overflow. 1L << 63 is the same bit pattern as Long.MIN_VALUE.
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVector(Enum64.class, 1L << 31));
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVector(Enum64.class, 1L << 32));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, 1L << 63));
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, Long.MIN_VALUE));
    }
}
