package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that the Symmetry010 calendar rejects the leap-week day (December 37)
 * in years that are <em>not</em> leap years.
 * <p>
 * In Symmetry010, only leap years gain an extra week at the end of December,
 * making December run from day 1 to day 37. Asking for day 37 of December in a
 * non-leap year must therefore fail with a {@link DateTimeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_badLeapDayDates {

    /** The leap-week day only exists in leap years. */
    private static final int DECEMBER = 12;
    private static final int LEAP_WEEK_DAY = 37;

    /** Proleptic years that are NOT Symmetry010 leap years, so December has no day 37. */
    static int[] nonLeapYears() {
        return new int[] { 1, 100, 200, 2000 };
    }

    @ParameterizedTest
    @MethodSource("nonLeapYears")
    public void of_rejectsLeapWeekDay_inNonLeapYear(int nonLeapYear) {
        assertThrows(DateTimeException.class,
                () -> Symmetry010Date.of(nonLeapYear, DECEMBER, LEAP_WEEK_DAY));
    }
}
