package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_era_loop {

    @Test
    public void test_era_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            JulianDate julianDate = JulianChronology.INSTANCE.date(prolepticYear, 1, 1);

            // The proleptic year is stored as-is (0 and negatives represent BC years)
            assertEquals(prolepticYear, julianDate.get(YEAR));

            // Proleptic year <= 0 belongs to the BC era; year > 0 belongs to AD
            JulianEra expectedEra = (prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(expectedEra, julianDate.getEra());

            // Year-of-era counts from 1 outward in both directions:
            //   BC: proleptic year 0 -> yoe 1, proleptic year -1 -> yoe 2, ...
            //   AD: proleptic year 1 -> yoe 1, proleptic year 2 -> yoe 2, ...
            int expectedYearOfEra = (prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear);
            assertEquals(expectedYearOfEra, julianDate.get(YEAR_OF_ERA));

            // Round-trip: constructing from (era, yearOfEra) must produce the same date
            JulianDate eraBasedDate = JulianChronology.INSTANCE.date(expectedEra, expectedYearOfEra, 1, 1);
            assertEquals(julianDate, eraBasedDate);
        }
    }
}
