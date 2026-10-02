package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectorsFromArray extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    private enum Enum64 {
        A0, A1, A2, A3, A4, A5, A6, A7,
        A8, A9, A10, A11, A12, A13, A14, A15,
        A16, A17, A18, A19, A20, A21, A22, A23,
        A24, A25, A26, A27, A28, A29, A30, A31,
        A32, A33, A34, A35, A36, A37, A38, A39,
        A40, A41, A42, A43, A44, A45, A46, A47,
        A48, A49, A50, A51, A52, A53, A54, A55,
        A56, A57, A58, A59, A60, A61, A62, A63
    }

    private enum TooMany {
        A0, A1, A2, A3, A4, A5, A6, A7,
        A8, A9, A10, A11, A12, A13, A14, A15,
        A16, A17, A18, A19, A20, A21, A22, A23,
        A24, A25, A26, A27, A28, A29, A30, A31,
        A32, A33, A34, A35, A36, A37, A38, A39,
        A40, A41, A42, A43, A44, A45, A46, A47,
        A48, A49, A50, A51, A52, A53, A54, A55,
        A56, A57, A58, A59, A60, A61, A62, L2,
        M2
    }

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGenerateBitVectorsFromArray() {
        assertEmptyTrafficSelection();
        assertSingleTrafficSelections();
        assertMultipleTrafficSelections();
        assertDuplicateTrafficSelectionsAreCondensed();
        assertSixtyFourValueEnumUsesLongBitPositions();
        assertEnumsLargerThanSixtyFourValuesUseMultipleLongs();
    }

    private void assertEmptyTrafficSelection() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class), new long[] { 0L });
    }

    private void assertSingleTrafficSelections() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED), 1L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER), 2L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.GREEN), 4L);
    }

    private void assertMultipleTrafficSelections() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER), 3L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.GREEN), 5L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER, Traffic.GREEN), 6L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);
    }

    private void assertDuplicateTrafficSelectionsAreCondensed() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN), 7L);
    }

    private void assertSixtyFourValueEnumUsesLongBitPositions() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A31), 1L << 31);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A32), 1L << 32);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), 1L << 63);
        assertLongArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), Long.MIN_VALUE);
    }

    private void assertEnumsLargerThanSixtyFourValuesUseMultipleLongs() {
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.M2), 1L, 0L);
        assertLongArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.L2, TooMany.M2), 1L, 1L << 63);
    }
}
