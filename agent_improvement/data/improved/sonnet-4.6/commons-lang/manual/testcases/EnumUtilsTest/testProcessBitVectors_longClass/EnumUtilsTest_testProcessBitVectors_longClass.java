package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVectors_longClass extends AbstractLangTest {

    @Test
    void testProcessBitVectors_longClass() {
        // Single long: bits 0-2 map to ordinals of A (0), B (1), C (2)
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L));
        assertEquals(EnumSet.of(TooMany.A),                         EnumUtils.processBitVectors(TooMany.class, 1L));
        assertEquals(EnumSet.of(TooMany.B),                         EnumUtils.processBitVectors(TooMany.class, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B),              EnumUtils.processBitVectors(TooMany.class, 3L));
        assertEquals(EnumSet.of(TooMany.C),                         EnumUtils.processBitVectors(TooMany.class, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C),              EnumUtils.processBitVectors(TooMany.class, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C),              EnumUtils.processBitVectors(TooMany.class, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C),   EnumUtils.processBitVectors(TooMany.class, 7L));

        // Two longs with high word = 0: results are identical to the single-long case above
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L, 0L));
        assertEquals(EnumSet.of(TooMany.A),                         EnumUtils.processBitVectors(TooMany.class, 0L, 1L));
        assertEquals(EnumSet.of(TooMany.B),                         EnumUtils.processBitVectors(TooMany.class, 0L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B),              EnumUtils.processBitVectors(TooMany.class, 0L, 3L));
        assertEquals(EnumSet.of(TooMany.C),                         EnumUtils.processBitVectors(TooMany.class, 0L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C),              EnumUtils.processBitVectors(TooMany.class, 0L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C),              EnumUtils.processBitVectors(TooMany.class, 0L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C),   EnumUtils.processBitVectors(TooMany.class, 0L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C),   EnumUtils.processBitVectors(TooMany.class, 0L, 7L));

        // Two longs with high word = 1: bit 0 of the high word activates M2 (ordinal 64)
        assertEquals(EnumSet.of(TooMany.M2),                                EnumUtils.processBitVectors(TooMany.class, 1L, 0L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.M2),                     EnumUtils.processBitVectors(TooMany.class, 1L, 1L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.M2),                     EnumUtils.processBitVectors(TooMany.class, 1L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2),          EnumUtils.processBitVectors(TooMany.class, 1L, 3L));
        assertEquals(EnumSet.of(TooMany.C, TooMany.M2),                     EnumUtils.processBitVectors(TooMany.class, 1L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2),          EnumUtils.processBitVectors(TooMany.class, 1L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2),          EnumUtils.processBitVectors(TooMany.class, 1L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 7L));

        // High word = 9 (binary 1001): extra set bits beyond M2's position are ignored
        assertEquals(EnumSet.of(TooMany.M2),                                EnumUtils.processBitVectors(TooMany.class, 9L, 0L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.M2),                     EnumUtils.processBitVectors(TooMany.class, 9L, 1L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.M2),                     EnumUtils.processBitVectors(TooMany.class, 9L, 2L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2),          EnumUtils.processBitVectors(TooMany.class, 9L, 3L));
        assertEquals(EnumSet.of(TooMany.C, TooMany.M2),                     EnumUtils.processBitVectors(TooMany.class, 9L, 4L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2),          EnumUtils.processBitVectors(TooMany.class, 9L, 5L));
        assertEquals(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2),          EnumUtils.processBitVectors(TooMany.class, 9L, 6L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 7L));
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 7L));
    }
}
