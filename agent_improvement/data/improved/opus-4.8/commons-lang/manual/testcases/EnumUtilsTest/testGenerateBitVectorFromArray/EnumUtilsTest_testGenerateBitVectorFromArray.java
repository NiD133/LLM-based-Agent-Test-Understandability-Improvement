package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#generateBitVector(Class, Enum...)} when called with a
 * varargs array of enum constants.
 *
 * <p>{@code generateBitVector} encodes a set of enum constants into a single
 * {@code long}, where each constant contributes the bit {@code 1L << ordinal()}.
 * The expected values below are therefore the bitwise OR of those per-constant
 * bits. For {@code Traffic}: RED = ordinal 0 = bit 1, AMBER = ordinal 1 = bit 2,
 * GREEN = ordinal 2 = bit 4.</p>
 */
public class EnumUtilsTest_testGenerateBitVectorFromArray extends AbstractLangTest {

    @Test
    void testGenerateBitVectorFromArray() {
        // No constants -> no bits set.
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class));

        // A single constant sets exactly the bit at its ordinal.
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED));
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER));
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, Traffic.GREEN));

        // Multiple constants OR their individual bits together.
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER));
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.GREEN));
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER, Traffic.GREEN));
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN));

        // Duplicate constants are idempotent: the repeated bit is OR-ed in only once.
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));

        // A 64-value enum exercises the full long range and confirms the high bits
        // are set correctly without any int<->long conversion truncation.
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, Enum64.A31));
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, Enum64.A32));
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
        // The bit for ordinal 63 is the sign bit, i.e. Long.MIN_VALUE.
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }
}
