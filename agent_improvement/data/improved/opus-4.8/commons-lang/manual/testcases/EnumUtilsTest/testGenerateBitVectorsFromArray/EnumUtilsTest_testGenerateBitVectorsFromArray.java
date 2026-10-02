package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#generateBitVectors(Class, Enum...)}.
 *
 * <p>{@code generateBitVectors} encodes a subset of enum constants as a
 * {@code long[]}. Each constant sets the bit at its ordinal position: ordinal 0
 * maps to bit 0 (value 1), ordinal 1 to bit 1 (value 2), and so on. Enums with
 * more than 64 constants need more than one {@code long}, so the result uses as
 * many {@code long}s as required, most-significant element first.</p>
 */
public class EnumUtilsTest_testGenerateBitVectorsFromArray extends AbstractLangTest {

    /**
     * Asserts that the bit vector produced by {@code generateBitVectors} matches
     * the {@code expected} {@code long}s. The expected values are listed
     * most-significant element first, mirroring the encoding's layout.
     */
    private void assertBitVectorEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGenerateBitVectorsFromArray() {
        // No constants selected -> all bits clear.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class), new long[] { 0L });

        // A single constant sets only the bit at its ordinal (RED=0, AMBER=1, GREEN=2).
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED), 1L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER), 2L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.GREEN), 4L);

        // Multiple constants OR their individual bits together.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER), 3L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.GREEN), 5L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER, Traffic.GREEN), 6L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);

        // Duplicate constants are ignored; the result is the same as the distinct set.
        assertBitVectorEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN), 7L);

        // Exactly 64 constants still fit in one long: high ordinals must not lose
        // bits to an int<->long conversion. A63 sets the sign bit (Long.MIN_VALUE).
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A31), 1L << 31);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A32), 1L << 32);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), 1L << 63);
        assertBitVectorEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), Long.MIN_VALUE);

        // More than 64 constants need two longs. The result is most-significant first:
        // M2 lands in the low long; L2 (a higher ordinal) lands in the high long.
        assertBitVectorEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.M2), 1L, 0L);
        assertBitVectorEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.L2, TooMany.M2), 1L, 1L << 63);
    }
}
