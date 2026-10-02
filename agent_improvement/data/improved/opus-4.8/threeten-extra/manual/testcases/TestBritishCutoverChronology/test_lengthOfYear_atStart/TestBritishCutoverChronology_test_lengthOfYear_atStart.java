package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests {@link BritishCutoverDate#lengthOfYear()} evaluated on the first day of
 * each year (January 1st).
 *
 * <p>The British cutover calendar follows the Julian rules up to 1752 and the
 * Gregorian rules afterwards. The notable special case is 1752 itself: eleven
 * days (3rd to 13th September) were dropped during the switch, so that year only
 * has 355 days.
 */
public class TestBritishCutoverChronology_test_lengthOfYear_atStart {

    /**
     * Each case is a (year, expected length in days) pair for a year starting on
     * January 1st.
     */
    public static Stream<Arguments> data_lengthOfYear() {
        return Stream.of(
            // Julian leap-year rule: every 4th year is a leap year.
            Arguments.of(-101, 365),
            Arguments.of(-100, 366),
            Arguments.of(-99, 365),
            Arguments.of(-1, 365),
            Arguments.of(0, 366),
            Arguments.of(100, 366),
            Arguments.of(1600, 366),
            Arguments.of(1700, 366),
            // Years around the 1752 cutover.
            Arguments.of(1748, 366),
            Arguments.of(1749, 365),
            Arguments.of(1750, 365),
            Arguments.of(1751, 365),
            Arguments.of(1752, 355),  // 11 days dropped during the Julian-to-Gregorian switch
            Arguments.of(1753, 365),
            // Century years: leap under Julian rules (pre-1752), non-leap under
            // Gregorian rules unless divisible by 400 (post-1752).
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
            Arguments.of(2100, 365)
        );
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfYear")
    public void test_lengthOfYear_atStart(int year, int expectedLength) {
        BritishCutoverDate firstDayOfYear = BritishCutoverDate.of(year, 1, 1);
        assertEquals(expectedLength, firstDayOfYear.lengthOfYear());
    }
}
