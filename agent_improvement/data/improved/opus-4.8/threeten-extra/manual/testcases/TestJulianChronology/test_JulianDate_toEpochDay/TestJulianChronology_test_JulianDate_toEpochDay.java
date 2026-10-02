package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link JulianDate#toEpochDay()} produces the same epoch-day
 * value as the equivalent ISO {@link LocalDate}.
 * <p>
 * The epoch day is a calendar-independent day count (day 0 is 1970-01-01).
 * A Julian date and the ISO date that fall on the same physical day must
 * therefore share an identical epoch-day value, even though their displayed
 * year/month/day differ (for example, {@code 0001-01-01 (Julian)} is the same
 * day as {@code 0000-12-30 (ISO)}).
 */
public class TestJulianChronology_test_JulianDate_toEpochDay {

    /**
     * Pairs of dates that refer to the same physical day, expressed first in the
     * Julian calendar and then in the ISO calendar. Each row asserts that both
     * representations resolve to the identical epoch day.
     */
    private static final Object[][] EQUIVALENT_JULIAN_AND_ISO_DATES = {
        // Around the start of the Julian epoch, where the calendars are offset.
        { JulianDate.of(1, 1, 1),       LocalDate.of(0, 12, 30) },
        { JulianDate.of(1, 1, 2),       LocalDate.of(0, 12, 31) },
        { JulianDate.of(1, 1, 3),       LocalDate.of(1, 1, 1) },
        { JulianDate.of(1, 2, 28),      LocalDate.of(1, 2, 26) },
        { JulianDate.of(1, 3, 1),       LocalDate.of(1, 2, 27) },
        { JulianDate.of(1, 3, 2),       LocalDate.of(1, 2, 28) },
        { JulianDate.of(1, 3, 3),       LocalDate.of(1, 3, 1) },

        // Year 4: a leap year in both calendars (Feb 29 exists).
        { JulianDate.of(4, 2, 28),      LocalDate.of(4, 2, 26) },
        { JulianDate.of(4, 2, 29),      LocalDate.of(4, 2, 27) },
        { JulianDate.of(4, 3, 1),       LocalDate.of(4, 2, 28) },
        { JulianDate.of(4, 3, 2),       LocalDate.of(4, 2, 29) },
        { JulianDate.of(4, 3, 3),       LocalDate.of(4, 3, 1) },

        // Year 100: a leap year in Julian but NOT in ISO/Gregorian, so the
        // calendars drift apart by one more day after Julian's Feb 29.
        { JulianDate.of(100, 2, 28),    LocalDate.of(100, 2, 26) },
        { JulianDate.of(100, 2, 29),    LocalDate.of(100, 2, 27) },
        { JulianDate.of(100, 3, 1),     LocalDate.of(100, 2, 28) },
        { JulianDate.of(100, 3, 2),     LocalDate.of(100, 3, 1) },
        { JulianDate.of(100, 3, 3),     LocalDate.of(100, 3, 2) },

        // Just before the Julian epoch (proleptic year 0).
        { JulianDate.of(0, 12, 31),     LocalDate.of(0, 12, 29) },
        { JulianDate.of(0, 12, 30),     LocalDate.of(0, 12, 28) },

        // Historical and modern dates, where the offset has grown to days.
        { JulianDate.of(1582, 10, 4),   LocalDate.of(1582, 10, 14) },
        { JulianDate.of(1582, 10, 5),   LocalDate.of(1582, 10, 15) },
        { JulianDate.of(1945, 10, 30),  LocalDate.of(1945, 11, 12) },
        { JulianDate.of(2012, 6, 22),   LocalDate.of(2012, 7, 5) },
        { JulianDate.of(2012, 6, 23),   LocalDate.of(2012, 7, 6) },
    };

    @Test
    public void julianDateHasSameEpochDayAsEquivalentIsoDate() {
        for (Object[] datePair : EQUIVALENT_JULIAN_AND_ISO_DATES) {
            JulianDate julianDate = (JulianDate) datePair[0];
            LocalDate isoDate = (LocalDate) datePair[1];

            assertEquals(isoDate.toEpochDay(), julianDate.toEpochDay());
        }
    }
}
