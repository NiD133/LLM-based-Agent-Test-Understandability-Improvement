package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGenerateBitVectors() {
        // Traffic enum: RED=ordinal 0 (bit 0), AMBER=ordinal 1 (bit 1), GREEN=ordinal 2 (bit 2).
        // For enums with ≤64 values, generateBitVectors returns a single-element long[].
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class)), 0b000L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED)),           0b001L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER)),         0b010L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN)),         0b100L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED,   Traffic.AMBER)),            0b011L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED,   Traffic.GREEN)),            0b101L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)),            0b110L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED,   Traffic.AMBER, Traffic.GREEN)), 0b111L);

        // Enum64 has exactly 64 values (ordinals 0–63). These assertions verify that high ordinals
        // are handled with long shifts, not int shifts — an int shift of ≥32 would give wrong results.
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A31)), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A32)), 1L << 32);
        // A63 occupies bit 63, which equals Long.MIN_VALUE in two's-complement; both forms are asserted.
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), Long.MIN_VALUE);

        // TooMany has 65 values, so generateBitVectors returns a two-element long[]:
        //   result[0] = high word (ordinals 64+), result[1] = low word (ordinals 0–63).
        // M2 is ordinal 64 → bit 0 of the high word: {high=1L, low=0L}.
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.M2)),             1L, 0L);
        // L2 is ordinal 63 → bit 63 of the low word (= Long.MIN_VALUE = 1L<<63).
        // M2 is ordinal 64 → bit 0 of the high word (= 1L).
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2)), 1L, 1L << 63);
    }
}
