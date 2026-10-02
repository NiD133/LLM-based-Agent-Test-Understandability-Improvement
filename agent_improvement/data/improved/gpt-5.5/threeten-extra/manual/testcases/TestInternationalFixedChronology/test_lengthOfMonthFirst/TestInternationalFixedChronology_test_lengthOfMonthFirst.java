package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonthFirst {

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
                { 1900, 1, 28, 28 },
                { 1900, 2, 28, 28 },
                { 1900, 3, 28, 28 },
                { 1900, 4, 28, 28 },
                { 1900, 5, 28, 28 },
                { 1900, 6, 28, 28 },
                { 1900, 7, 28, 28 },
                { 1900, 8, 28, 28 },
                { 1900, 9, 28, 28 },
                { 1900, 10, 28, 28 },
                { 1900, 11, 28, 28 },
                { 1900, 12, 28, 28 },
                { 1900, 13, 29, 29 },
                { 1904, 6, 29, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int originalDayValue, int expectedLength) {
        assertEquals(expectedLength, InternationalFixedDate.of(year, month, 1).lengthOfMonth());
    }
}
