package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_era_yearDay_loop {

    @Test
    public void test_era_yearDay_loop() {
        for (int year = -200; year < 200; year++) {
            // Create day 1 of the proleptic year and confirm the YEAR field round-trips
            JulianDate base = JulianChronology.INSTANCE.dateYearDay(year, 1);
            assertEquals(year, base.get(YEAR));

            // Julian calendar: proleptic year <= 0 maps to BC, year > 0 maps to AD
            JulianEra era = (year <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(era, base.getEra());

            // Year-of-era counts from 1 in both directions:
            // AD years: yearOfEra == prolepticYear; BC years: yearOfEra == 1 - prolepticYear
            int yearOfEra = (year <= 0 ? 1 - year : year);
            assertEquals(yearOfEra, base.get(YEAR_OF_ERA));

            // Building the same date via era + yearOfEra must produce an equal date
            JulianDate eraBased = JulianChronology.INSTANCE.dateYearDay(era, yearOfEra, 1);
            assertEquals(base, eraBased);
        }
    }
}
