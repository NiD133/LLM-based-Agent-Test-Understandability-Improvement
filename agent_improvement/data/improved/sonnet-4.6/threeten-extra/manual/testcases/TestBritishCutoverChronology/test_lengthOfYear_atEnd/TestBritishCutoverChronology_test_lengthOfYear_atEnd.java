package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_lengthOfYear_atEnd {

    public static Object[][] data_lengthOfYear() {
        return new Object[][] {
            { -101, 365 },
            { -100, 366 }, // Julian leap year (divisible by 4, no Gregorian exception)
            {  -99, 365 },
            {   -1, 365 },
            {    0, 366 }, // Julian leap year (divisible by 4)
            {  100, 366 }, // Julian leap year (Julian ignores century rule before cutover)
            { 1600, 366 }, // Julian leap year
            { 1700, 366 }, // Julian leap year (Gregorian would be 365, but before cutover)
            { 1751, 365 }, // last full year before the cutover, not a leap year
            { 1748, 366 },
            { 1749, 365 },
            { 1750, 365 },
            { 1751, 365 },
            { 1752, 355 }, // cutover year: 11 days skipped in September
            { 1753, 365 },
            { 1500, 366 },
            { 1600, 366 },
            { 1700, 366 },
            { 1800, 365 }, // Gregorian non-leap (century not divisible by 400)
            { 1900, 365 }, // Gregorian non-leap
            { 1901, 365 },
            { 1902, 365 },
            { 1903, 365 },
            { 1904, 366 }, // Gregorian leap year
            { 2000, 366 }, // Gregorian leap year (divisible by 400)
            { 2100, 365 }, // Gregorian non-leap (century not divisible by 400)
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfYear")
    public void test_lengthOfYear_atEnd(int year, int length) {
        assertEquals(length, BritishCutoverDate.of(year, 12, 31).lengthOfYear());
    }
}
