package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#generateBitVector(Class, Iterable)}.
 *
 * <p>{@code generateBitVector} encodes a set of enum constants into a {@code long}, setting the
 * bit at position {@code ordinal()} for every constant present in the set. So for the
 * {@code Traffic} enum (RED = ordinal 0, AMBER = ordinal 1, GREEN = ordinal 2) the resulting
 * value is simply the OR of {@code 1L << ordinal} over the chosen constants.</p>
 */
public class EnumUtilsTest_testGenerateBitVector extends AbstractLangTest {

    @Test
    void testGenerateBitVector() {
        // Empty set -> no bits set.
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class, EnumSet.noneOf(Traffic.class)));

        // Single constants set their own ordinal bit: RED=bit0, AMBER=bit1, GREEN=bit2.
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED)));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER)));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.GREEN)));

        // Multiple constants OR their bits together.
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)));
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)));

        // A 64-value enum exercises the high bits, confirming there is no int<->long conversion
        // issue around bit 31 (where an int shift would overflow) or at the sign bit (bit 63).
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A31)));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A32)));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
        // Bit 63 is the sign bit, so the value equals Long.MIN_VALUE.
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
    }
}
