package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#generateBitVectors(Class, Iterable)}.
 *
 * <p>{@code generateBitVectors} encodes a subset of an enum as a {@code long[]} bit vector.
 * Each enum constant maps to the bit at its ordinal position; constant {@code N} sets bit
 * {@code N}. Because a {@code long} only holds 64 bits, enums with more than 64 constants
 * spill into additional {@code long}s, and the returned array is ordered with the most
 * significant block first (least significant digits rightmost).</p>
 */
public class EnumUtilsTest_testGenerateBitVectors extends AbstractLangTest {

    /**
     * Asserts that the bit vector {@code actual} equals the expected sequence of {@code long}
     * blocks. The expected blocks are listed most-significant first, matching the order returned
     * by {@link EnumUtils#generateBitVectors}.
     */
    private void assertBitVectorEquals(final long[] actual, final long... expectedBlocks) {
        assertArrayEquals(expectedBlocks, actual);
    }

    @Test
    void testGenerateBitVectors() {
        // Empty subset -> no bits set.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class)), 0L);

        // Single constant -> only the bit at that constant's ordinal is set.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED)), 1L);   // ordinal 0 -> bit 0
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER)), 2L); // ordinal 1 -> bit 1
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN)), 4L); // ordinal 2 -> bit 2

        // Multiple constants -> the set bits are OR-ed together.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)), 3L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)), 5L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)), 6L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)), 7L);

        // Enum with exactly 64 values: the high ordinals exercise the int<->long boundary,
        // confirming bit 31, 32 and 63 are shifted correctly within a single long.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A31)), 1L << 31);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A32)), 1L << 32);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), 1L << 63);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), Long.MIN_VALUE); // 1L << 63 == Long.MIN_VALUE

        // Enum with more than 64 values: the result spans two longs, most significant block first.
        // M2 sits in the low block; L2 sits in the high block.
        assertBitVectorEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.M2)), 1L, 0L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2)), 1L, 1L << 63);
    }
}
