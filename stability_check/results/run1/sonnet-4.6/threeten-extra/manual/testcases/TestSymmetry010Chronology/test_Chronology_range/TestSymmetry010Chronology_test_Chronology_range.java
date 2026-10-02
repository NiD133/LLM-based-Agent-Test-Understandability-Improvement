package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        Symmetry010Chronology chrono = Symmetry010Chronology.INSTANCE;

        // Day-of-week alignment: always Monday(1)–Sunday(7)
        assertEquals(ValueRange.of(1, 7), chrono.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), chrono.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(1, 7), chrono.range(DAY_OF_WEEK));

        // Week-of-period: normal months have 4 weeks; leap December adds a 5th.
        // Normal years have 52 weeks; leap years add a 53rd.
        assertEquals(ValueRange.of(1, 4, 5), chrono.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(1, 52, 53), chrono.range(ALIGNED_WEEK_OF_YEAR));

        // Day-of-period: months are 30 days (31 for Feb/May/Aug/Nov; 37 for leap December).
        // Normal years have 364 days; leap years have 371.
        assertEquals(ValueRange.of(1, 30, 37), chrono.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 364, 371), chrono.range(DAY_OF_YEAR));

        // Calendar structure fields
        assertEquals(ValueRange.of(1, 12), chrono.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(0, 1),  chrono.range(ERA));

        // Year and derived fields, supporting +/- 1,000,000 proleptic years
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000),    chrono.range(YEAR));
        assertEquals(ValueRange.of(-1_000_000,  1_000_000),    chrono.range(YEAR_OF_ERA));
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), chrono.range(PROLEPTIC_MONTH));

        // EPOCH_DAY: days counted from the ISO epoch (1970-01-01).
        // The range is derived from ±1,000,000 years, adjusted for leap weeks and
        // the fixed offset between the Sym010 epoch (year 1) and the ISO epoch.
        long maxYearDays    = 1_000_000L * 364;   // days in 1,000,000 non-leap years
        long leapWeekDays   = 177_474L * 7;        // extra days from leap weeks before year 1,000,000
        long epochDayOffset = 719_162L;            // days from Sym010 CE 1/01/01 to ISO 1970-01-01
        assertEquals(
            ValueRange.of(-maxYearDays - leapWeekDays - epochDayOffset,
                           maxYearDays + leapWeekDays - epochDayOffset),
            chrono.range(EPOCH_DAY));
    }
}
