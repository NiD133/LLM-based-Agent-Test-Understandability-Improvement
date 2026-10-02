package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link JulianDate} created from a proleptic year is consistent
 * with the equivalent date created from its era and year-of-era.
 */
public class TestJulianChronology_test_era_yearDay_loop {

    /**
     * For every proleptic year in a range spanning both eras, verify that:
     * <ul>
     *   <li>the date reports the same proleptic year it was built from,</li>
     *   <li>years at or before 0 fall in the BC era and later years in the AD era,</li>
     *   <li>the year-of-era is derived correctly for each era, and</li>
     *   <li>building the same date from (era, year-of-era) yields an equal date.</li>
     * </ul>
     */
    @Test
    public void test_era_yearDay_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            JulianDate dateFromProlepticYear = JulianChronology.INSTANCE.dateYearDay(prolepticYear, 1);
            assertEquals(prolepticYear, dateFromProlepticYear.get(YEAR));

            JulianEra expectedEra = (prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(expectedEra, dateFromProlepticYear.getEra());

            int expectedYearOfEra = (prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear);
            assertEquals(expectedYearOfEra, dateFromProlepticYear.get(YEAR_OF_ERA));

            JulianDate dateFromEra = JulianChronology.INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, 1);
            assertEquals(dateFromProlepticYear, dateFromEra);
        }
    }
}
