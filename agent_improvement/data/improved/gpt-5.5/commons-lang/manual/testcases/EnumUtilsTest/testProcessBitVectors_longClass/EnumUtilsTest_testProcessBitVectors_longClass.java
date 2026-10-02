package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testProcessBitVectors_longClass extends AbstractLangTest {

    private enum TooMany {
        A,
        B,
        C,
        X3,
        X4,
        X5,
        X6,
        X7,
        X8,
        X9,
        X10,
        X11,
        X12,
        X13,
        X14,
        X15,
        X16,
        X17,
        X18,
        X19,
        X20,
        X21,
        X22,
        X23,
        X24,
        X25,
        X26,
        X27,
        X28,
        X29,
        X30,
        X31,
        X32,
        X33,
        X34,
        X35,
        X36,
        X37,
        X38,
        X39,
        X40,
        X41,
        X42,
        X43,
        X44,
        X45,
        X46,
        X47,
        X48,
        X49,
        X50,
        X51,
        X52,
        X53,
        X54,
        X55,
        X56,
        X57,
        X58,
        X59,
        X60,
        X61,
        X62,
        X63,
        M2
    }

    @Test
    void testProcessBitVectors_longClass() {
        assertSingleLongVectors();
        assertTwoLongVectorsWithoutHighBits();
        assertTwoLongVectorsWithM2Bit();
        assertTwoLongVectorsIgnoreIrrelevantHighBits();
    }

    private void assertSingleLongVectors() {
        assertBitVectors(EnumSet.noneOf(TooMany.class), 0L);
        assertBitVectors(EnumSet.of(TooMany.A), 1L);
        assertBitVectors(EnumSet.of(TooMany.B), 2L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B), 3L);
        assertBitVectors(EnumSet.of(TooMany.C), 4L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.C), 5L);
        assertBitVectors(EnumSet.of(TooMany.B, TooMany.C), 6L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), 7L);
    }

    private void assertTwoLongVectorsWithoutHighBits() {
        assertBitVectors(EnumSet.noneOf(TooMany.class), 0L, 0L);
        assertBitVectors(EnumSet.of(TooMany.A), 0L, 1L);
        assertBitVectors(EnumSet.of(TooMany.B), 0L, 2L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B), 0L, 3L);
        assertBitVectors(EnumSet.of(TooMany.C), 0L, 4L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.C), 0L, 5L);
        assertBitVectors(EnumSet.of(TooMany.B, TooMany.C), 0L, 6L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), 0L, 7L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), 0L, 7L);
    }

    private void assertTwoLongVectorsWithM2Bit() {
        assertBitVectors(EnumSet.of(TooMany.M2), 1L, 0L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.M2), 1L, 1L);
        assertBitVectors(EnumSet.of(TooMany.B, TooMany.M2), 1L, 2L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), 1L, 3L);
        assertBitVectors(EnumSet.of(TooMany.C, TooMany.M2), 1L, 4L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), 1L, 5L);
        assertBitVectors(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), 1L, 6L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), 1L, 7L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), 1L, 7L);
    }

    private void assertTwoLongVectorsIgnoreIrrelevantHighBits() {
        assertBitVectors(EnumSet.of(TooMany.M2), 9L, 0L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.M2), 9L, 1L);
        assertBitVectors(EnumSet.of(TooMany.B, TooMany.M2), 9L, 2L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), 9L, 3L);
        assertBitVectors(EnumSet.of(TooMany.C, TooMany.M2), 9L, 4L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), 9L, 5L);
        assertBitVectors(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), 9L, 6L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), 9L, 7L);
        assertBitVectors(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), 9L, 7L);
    }

    private void assertBitVectors(final EnumSet<?> expected, final long... bitVectors) {
        assertEquals(expected, EnumUtils.processBitVectors(TooMany.class, bitVectors));
    }
}
