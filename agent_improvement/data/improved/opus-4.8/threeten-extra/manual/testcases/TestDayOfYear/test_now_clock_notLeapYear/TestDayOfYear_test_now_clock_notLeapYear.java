package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the {@code DayOfYear.now(...)} factory methods.
 * <p>
 * Each test confirms that {@code DayOfYear.now} reports the same day-of-year as
 * the {@link LocalDate} it is derived from, using the same time source.
 */
public class TestDayOfYear_test_now_clock_notLeapYear {

    /** Number of days in a standard (non-leap) year. */
    private static final int STANDARD_YEAR_LENGTH = 365;

    /** A fixed time-zone used so the test does not depend on the machine's default zone. */
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    //-----------------------------------------------------------------------
    // now() - uses the system clock in the default time-zone
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_usesSystemClock_matchesLocalDate() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        int actualDayOfYear = DayOfYear.now().getValue();

        assertEquals(expectedDayOfYear, actualDayOfYear);
    }

    //-----------------------------------------------------------------------
    // now(ZoneId) - uses the system clock in an explicit time-zone
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_withZone_matchesLocalDateInSameZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");

        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        int actualDayOfYear = DayOfYear.now(tokyo).getValue();

        assertEquals(expectedDayOfYear, actualDayOfYear);
    }

    //-----------------------------------------------------------------------
    // now(Clock) - walk every day of a non-leap year (2007) and confirm
    // the day-of-year counts up from 1 to 365.
    //-----------------------------------------------------------------------
    @Test
    public void now_withClock_countsEveryDayOfNonLeapYear() {
        LocalDate date = LocalDate.of(2007, 1, 1);

        for (int expectedDayOfYear = 1; expectedDayOfYear <= STANDARD_YEAR_LENGTH; expectedDayOfYear++) {
            // Pin the clock to the start of the current day in a fixed zone.
            Instant startOfDay = date.atStartOfDay(PARIS).toInstant();
            Clock fixedClock = Clock.fixed(startOfDay, PARIS);

            DayOfYear actual = DayOfYear.now(fixedClock);

            assertEquals(expectedDayOfYear, actual.getValue());
            date = date.plusDays(1);
        }
    }
}
