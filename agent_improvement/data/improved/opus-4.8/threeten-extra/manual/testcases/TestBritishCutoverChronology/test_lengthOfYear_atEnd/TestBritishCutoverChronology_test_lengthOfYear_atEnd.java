package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests {@link BritishCutoverDate#lengthOfYear()} when the date is the last day
 * of the year (December 31).
 *
 * <p>The British cutover calendar follows the Julian calendar until September 1752,
 * then switches to the Gregorian calendar. The interesting cases are:
 * <ul>
 *   <li>Julian leap-year rule (every 4th year is a leap year) before 1752, e.g. 1700 has 366 days.</li>
 *   <li>1752 itself, which lost 11 days during the cutover and therefore has only 355 days.</li>
 *   <li>Gregorian leap-year rule (centuries are leap only when divisible by 400) after 1752,
 *       e.g. 1800 and 1900 have 365 days while 2000 has 366.</li>
 * </ul>
 */
public class TestBritishCutoverChronology_test_lengthOfYear_atEnd {

    /**
     * Provides each test case as (proleptic year, expected length of that year in days).
     */
    public static Stream<Arguments> data_lengthOfYear() {
        return Stream.of(
                // Julian era: every 4th year is a leap year (366 days)
                Arguments.of(-101, 365),
                Arguments.of(-100, 366),
                Arguments.of(-99, 365),
                Arguments.of(-1, 365),
                Arguments.of(0, 366),
                Arguments.of(100, 366),
                Arguments.of(1600, 366),
                Arguments.of(1700, 366),
                Arguments.of(1751, 365),
                Arguments.of(1748, 366),
                Arguments.of(1749, 365),
                Arguments.of(1750, 365),
                Arguments.of(1751, 365),
                // 1752: the cutover year, 11 days dropped so only 355 days
                Arguments.of(1752, 355),
                Arguments.of(1753, 365),
                // Gregorian era: centuries are leap years only when divisible by 400
                Arguments.of(1500, 366),
                Arguments.of(1600, 366),
                Arguments.of(1700, 366),
                Arguments.of(1800, 365),
                Arguments.of(1900, 365),
                Arguments.of(1901, 365),
                Arguments.of(1902, 365),
                Arguments.of(1903, 365),
                Arguments.of(1904, 366),
                Arguments.of(2000, 366),
                Arguments.of(2100, 365));
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfYear")
    public void test_lengthOfYear_atEnd(int year, int expectedLength) {
        BritishCutoverDate lastDayOfYear = BritishCutoverDate.of(year, 12, 31);
        assertEquals(expectedLength, lastDayOfYear.lengthOfYear());
    }
}
