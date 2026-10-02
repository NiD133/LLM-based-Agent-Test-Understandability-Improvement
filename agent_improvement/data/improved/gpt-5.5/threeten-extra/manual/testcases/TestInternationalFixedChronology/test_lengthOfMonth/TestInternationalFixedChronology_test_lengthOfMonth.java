package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_lengthOfMonth {

    private static final int STANDARD_MONTH_LENGTH = 28;
    private static final int LONG_MONTH_LENGTH = 29;

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
                standardMonth(1900, 1),
                standardMonth(1900, 2),
                standardMonth(1900, 3),
                standardMonth(1900, 4),
                standardMonth(1900, 5),
                standardMonth(1900, 6),
                standardMonth(1900, 7),
                standardMonth(1900, 8),
                standardMonth(1900, 9),
                standardMonth(1900, 10),
                standardMonth(1900, 11),
                standardMonth(1900, 12),
                longMonth(1900, 13),
                longMonth(1904, 6),
        };
    }

    private static Object[] standardMonth(int year, int month) {
        return new Object[] {year, month, STANDARD_MONTH_LENGTH, STANDARD_MONTH_LENGTH};
    }

    private static Object[] longMonth(int year, int month) {
        return new Object[] {year, month, LONG_MONTH_LENGTH, LONG_MONTH_LENGTH};
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int length) {
        assertEquals(length, InternationalFixedDate.of(year, month, day).lengthOfMonth());
    }
}
