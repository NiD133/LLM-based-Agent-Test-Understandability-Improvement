package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Symmetry454Date#isLeapWeek()} recognises the leap week.
 * <p>
 * In the Symmetry454 calendar a leap year ends with an extra week appended to
 * December, extending that month from 28 to 35 days. The year 2015 is such a
 * leap year, so days 29 through 35 of December 2015 all fall within the leap
 * week and must report {@code isLeapWeek() == true}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_leapWeek {

    private static final int LEAP_YEAR = 2015;
    private static final int DECEMBER = 12;

    @Test
    public void everyDayInTheLeapWeekIsReportedAsLeapWeek() {
        for (int dayOfMonth = 29; dayOfMonth <= 35; dayOfMonth++) {
            Symmetry454Date dateInLeapWeek = Symmetry454Date.of(LEAP_YEAR, DECEMBER, dayOfMonth);

            assertTrue(dateInLeapWeek.isLeapWeek(),
                    "Expected day " + dayOfMonth + " of December " + LEAP_YEAR + " to be in the leap week");
        }
    }
}
