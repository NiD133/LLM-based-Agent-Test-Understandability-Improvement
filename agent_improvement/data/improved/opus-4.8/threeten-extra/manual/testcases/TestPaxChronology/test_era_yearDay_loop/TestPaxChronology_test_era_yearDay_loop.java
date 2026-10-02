package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PaxChronology#dateYearDay} agrees on the proleptic year,
 * era and year-of-era across a wide span of years, and that constructing a date
 * from an (era, year-of-era) pair yields the same date as the proleptic-year form.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_era_yearDay_loop {

    @Test
    public void test_era_yearDay_loop() {
        for (int year = -200; year < 200; year++) {
            // Build the first day of the year from its proleptic year.
            PaxDate base = PaxChronology.INSTANCE.dateYearDay(year, 1);
            assertEquals(year, base.get(YEAR));

            // Years 0 and below fall in the BCE era; positive years are CE.
            PaxEra expectedEra = (year <= 0 ? PaxEra.BCE : PaxEra.CE);
            assertEquals(expectedEra, base.getEra());

            // In BCE the year-of-era counts backwards, so proleptic year 0 is year-of-era 1.
            int expectedYearOfEra = (year <= 0 ? 1 - year : year);
            assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

            // Constructing from the (era, year-of-era) pair must reproduce the same date.
            PaxDate fromEra = PaxChronology.INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, 1);
            assertEquals(base, fromEra);
        }
    }
}
