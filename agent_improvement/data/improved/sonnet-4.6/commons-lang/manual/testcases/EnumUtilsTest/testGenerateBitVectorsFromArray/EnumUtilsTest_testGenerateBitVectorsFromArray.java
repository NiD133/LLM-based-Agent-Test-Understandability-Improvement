package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EnumUtils#generateBitVectors(Class, Enum[])}.
 *
 * Each enum value is represented by setting the bit at its ordinal position.
 * When more than 64 values exist, the result spans multiple longs with the
 * most-significant word first and the least-significant word last.
 */
public class EnumUtilsTest_testGenerateBitVectorsFromArray extends AbstractLangTest {

    // Traffic enum ordinals: RED=0, AMBER=1, GREEN=2

    @Test
    void testGenerateBitVectors_emptySelection_returnsZeroVector() {
        // No values selected → all bits clear, single zero word
        assertArrayEquals(
                new long[]{0L},
                EnumUtils.generateBitVectors(Traffic.class));
    }

    @Test
    void testGenerateBitVectors_singleTrafficValue_setsSingleBit() {
        // RED (ordinal 0) → bit 0 → 1
        assertArrayEquals(
                new long[]{1L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.RED));

        // AMBER (ordinal 1) → bit 1 → 2
        assertArrayEquals(
                new long[]{2L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER));

        // GREEN (ordinal 2) → bit 2 → 4
        assertArrayEquals(
                new long[]{4L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.GREEN));
    }

    @Test
    void testGenerateBitVectors_multipleTrafficValues_setsAllCorrespondingBits() {
        // RED (bit 0) | AMBER (bit 1) → 0b011 = 3
        assertArrayEquals(
                new long[]{3L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER));

        // RED (bit 0) | GREEN (bit 2) → 0b101 = 5
        assertArrayEquals(
                new long[]{5L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.GREEN));

        // AMBER (bit 1) | GREEN (bit 2) → 0b110 = 6
        assertArrayEquals(
                new long[]{6L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER, Traffic.GREEN));

        // RED | AMBER | GREEN → 0b111 = 7
        assertArrayEquals(
                new long[]{7L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN));
    }

    @Test
    void testGenerateBitVectors_duplicateValues_deduplicatedGracefully() {
        // Duplicate GREEN is silently ignored (internally backed by EnumSet);
        // the result equals the non-duplicate case.
        assertArrayEquals(
                new long[]{7L},
                EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));
    }

    @Test
    void testGenerateBitVectors_enum64HighOrdinals_noIntLongConversionIssue() {
        // Enum64 has exactly 64 values (A0..A63). These assertions verify that
        // ordinals >= 31 are not silently narrowed to int during the bit shift.

        // A31 (ordinal 31): 1L << 31 is positive; 1 << 31 (int) would be Integer.MIN_VALUE
        assertArrayEquals(
                new long[]{1L << 31},
                EnumUtils.generateBitVectors(Enum64.class, Enum64.A31));

        // A32 (ordinal 32): unreachable with a 32-bit int shift
        assertArrayEquals(
                new long[]{1L << 32},
                EnumUtils.generateBitVectors(Enum64.class, Enum64.A32));

        // A63 (ordinal 63): highest bit of a long, equal to Long.MIN_VALUE in two's complement
        assertArrayEquals(
                new long[]{1L << 63},
                EnumUtils.generateBitVectors(Enum64.class, Enum64.A63));
        assertArrayEquals(
                new long[]{Long.MIN_VALUE},
                EnumUtils.generateBitVectors(Enum64.class, Enum64.A63));
    }

    @Test
    void testGenerateBitVectors_moreThan64Values_usesTwoLongWords() {
        // TooMany has more than 64 values, so the result is a two-element array.
        // Layout: [mostSignificantWord, leastSignificantWord]
        // M2 falls in the most-significant word (index 0); its bit position there is 0 → value 1.
        assertArrayEquals(
                new long[]{1L, 0L},
                EnumUtils.generateBitVectors(TooMany.class, TooMany.M2));

        // L2 falls in the least-significant word (index 1) at bit 63 → value Long.MIN_VALUE = 1L << 63.
        // Combined with M2 (bit 0 of word 0), both words are non-zero.
        assertArrayEquals(
                new long[]{1L, 1L << 63},
                EnumUtils.generateBitVectors(TooMany.class, TooMany.L2, TooMany.M2));
    }
}
