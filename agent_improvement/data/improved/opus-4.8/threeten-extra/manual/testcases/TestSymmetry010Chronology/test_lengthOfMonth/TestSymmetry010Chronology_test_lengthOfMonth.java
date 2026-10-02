package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Verifies {@link Symmetry010Date#lengthOfMonth()}.
 * <p>
 * In the Symmetry010 calendar the months of each quarter follow a fixed
 * 30 / 31 / 30 day pattern, so the long (31-day) months are February, May,
 * August and November. In a leap year the final month, December, is extended
 * by a leap week, giving it 37 days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonth {

    /**
     * Each case is (year, month, dayOfMonth, expectedLengthOfMonth).
     * The day-of-month is incidental: it only has to be a valid day in the month
     * so that the date can be constructed.
     */
    public static Stream<Arguments> data_lengthOfMonth() {
        return Stream.of(
                // Year 2000 is a common year: the 30/31/30 pattern repeats each quarter.
                Arguments.of(2000, 1, 28, 30),   // January   - short
                Arguments.of(2000, 2, 28, 31),   // February  - long
                Arguments.of(2000, 3, 28, 30),   // March     - short
                Arguments.of(2000, 4, 28, 30),   // April     - short
                Arguments.of(2000, 5, 28, 31),   // May       - long
                Arguments.of(2000, 6, 28, 30),   // June      - short
                Arguments.of(2000, 7, 28, 30),   // July      - short
                Arguments.of(2000, 8, 28, 31),   // August    - long
                Arguments.of(2000, 9, 28, 30),   // September - short
                Arguments.of(2000, 10, 28, 30),  // October   - short
                Arguments.of(2000, 11, 28, 31),  // November  - long
                Arguments.of(2000, 12, 28, 30),  // December  - short

                // Year 2004 is a leap year: December absorbs the leap week (30 + 7 = 37 days).
                Arguments.of(2004, 12, 20, 37)
        );
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int expectedLength) {
        Symmetry010Date date = Symmetry010Date.of(year, month, day);

        assertEquals(expectedLength, date.lengthOfMonth());
    }
}
