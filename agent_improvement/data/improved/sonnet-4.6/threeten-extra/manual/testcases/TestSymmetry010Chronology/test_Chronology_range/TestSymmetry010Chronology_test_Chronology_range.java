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
        // Week-day alignment fields: always 1–7
        assertEquals(ValueRange.of(1, 7), Symmetry010Chronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), Symmetry010Chronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(1, 7), Symmetry010Chronology.INSTANCE.range(DAY_OF_WEEK));

        // Week-of-period: normal months have 4 weeks, leap-December has 5; normal years 52 weeks, leap years 53
        assertEquals(ValueRange.of(1, 4, 5), Symmetry010Chronology.INSTANCE.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(1, 52, 53), Symmetry010Chronology.INSTANCE.range(ALIGNED_WEEK_OF_YEAR));

        // Day-of-month: standard months 1–30, long months 1–31, leap-December 1–37
        assertEquals(ValueRange.of(1, 30, 37), Symmetry010Chronology.INSTANCE.range(DAY_OF_MONTH));

        // Day-of-year: standard year 364 days, leap year 371 days
        assertEquals(ValueRange.of(1, 364, 371), Symmetry010Chronology.INSTANCE.range(DAY_OF_YEAR));

        // Calendar-level fields
        assertEquals(ValueRange.of(0, 1), Symmetry010Chronology.INSTANCE.range(ERA));
        assertEquals(ValueRange.of(1, 12), Symmetry010Chronology.INSTANCE.range(MONTH_OF_YEAR));

        // Year fields: symmetric range around epoch, ±1_000_000
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000), Symmetry010Chronology.INSTANCE.range(YEAR));
        assertEquals(ValueRange.of(-1_000_000, 1_000_000), Symmetry010Chronology.INSTANCE.range(YEAR_OF_ERA));

        // Proleptic month: 12 months × ±1_000_000 years, offset by 1 for zero-based indexing
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), Symmetry010Chronology.INSTANCE.range(PROLEPTIC_MONTH));

        // Epoch-day: derived from MAX_YEAR (1_000_000), days-per-year (364), leap-weeks before MAX_YEAR
        // (177_474 leap years × 7 days each), and the fixed offset to the Unix epoch (719_162 days).
        long maxYear = 1_000_000L;
        long daysPerYear = 364L;
        long leapWeeksBeforeMaxYear = Symmetry010Chronology.getLeapYearsBefore(maxYear); // 177_474
        long daysPerLeapWeek = 7L;
        long daysFromYear1ToEpoch = Symmetry010Chronology.DAYS_0001_TO_1970; // 719_162
        long epochDayMax = maxYear * daysPerYear + leapWeeksBeforeMaxYear * daysPerLeapWeek - daysFromYear1ToEpoch;
        long epochDayMin = -maxYear * daysPerYear - leapWeeksBeforeMaxYear * daysPerLeapWeek - daysFromYear1ToEpoch;
        assertEquals(ValueRange.of(epochDayMin, epochDayMax), Symmetry010Chronology.INSTANCE.range(EPOCH_DAY));
    }
}
