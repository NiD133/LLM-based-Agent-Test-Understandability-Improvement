package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors extends AbstractLangTest {

    private <E extends Enum<E>> void assertGeneratedBitVectors(final Class<E> enumClass,
            final EnumSet<E> values, final long... expected) {
        assertArrayEquals(expected, EnumUtils.generateBitVectors(enumClass, values));
    }

    @Test
    void testGenerateBitVectors() {
        assertTrafficSignalsUseExpectedSingleLongBitMasks();
        assertHighOrdinalValuesUseLongShifts();
        assertEnumsLargerThanSixtyFourValuesUseMultipleLongs();
    }

    private void assertTrafficSignalsUseExpectedSingleLongBitMasks() {
        assertGeneratedBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class), 0L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.RED), 1L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER), 2L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN), 4L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER), 3L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN), 5L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN), 6L);
        assertGeneratedBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);
    }

    private void assertHighOrdinalValuesUseLongShifts() {
        assertGeneratedBitVectors(Enum64.class, EnumSet.of(Enum64.A31), 1L << 31);
        assertGeneratedBitVectors(Enum64.class, EnumSet.of(Enum64.A32), 1L << 32);
        assertGeneratedBitVectors(Enum64.class, EnumSet.of(Enum64.A63), 1L << 63);
        assertGeneratedBitVectors(Enum64.class, EnumSet.of(Enum64.A63), Long.MIN_VALUE);
    }

    private void assertEnumsLargerThanSixtyFourValuesUseMultipleLongs() {
        assertGeneratedBitVectors(TooMany.class, EnumSet.of(TooMany.M2), 1L, 0L);
        assertGeneratedBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2), 1L, 1L << 63);
    }

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
}
