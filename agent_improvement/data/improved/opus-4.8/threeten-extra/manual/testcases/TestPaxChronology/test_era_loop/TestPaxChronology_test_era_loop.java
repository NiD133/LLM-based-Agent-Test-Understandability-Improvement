package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_era_loop {

    /**
     * Walks a range of proleptic years and checks that, for the first day of each year,
     * the era-derived view of a date is consistent with its proleptic-year view:
     * <ul>
     * <li>years 0 and below belong to the BCE era, positive years to the CE era;
     * <li>year-of-era counts backwards through BCE (so proleptic year {@code y <= 0} maps to {@code 1 - y});
     * <li>building the date from (era, year-of-era) yields the same date as building it from the proleptic year.
     * </ul>
     */
    @Test
    public void test_era_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            PaxDate fromProlepticYear = PaxChronology.INSTANCE.date(prolepticYear, 1, 1);
            assertEquals(prolepticYear, fromProlepticYear.get(YEAR));

            PaxEra expectedEra = (prolepticYear <= 0 ? PaxEra.BCE : PaxEra.CE);
            assertEquals(expectedEra, fromProlepticYear.getEra());

            int expectedYearOfEra = (prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear);
            assertEquals(expectedYearOfEra, fromProlepticYear.get(YEAR_OF_ERA));

            PaxDate fromEraAndYearOfEra = PaxChronology.INSTANCE.date(expectedEra, expectedYearOfEra, 1, 1);
            assertEquals(fromProlepticYear, fromEraAndYearOfEra);
        }
    }
}
