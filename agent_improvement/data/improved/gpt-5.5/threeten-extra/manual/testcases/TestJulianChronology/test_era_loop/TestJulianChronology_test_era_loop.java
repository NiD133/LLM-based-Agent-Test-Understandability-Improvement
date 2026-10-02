package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_era_loop {

    private static final int FIRST_TESTED_YEAR = -200;
    private static final int FIRST_UNTESTED_YEAR = 200;

    @Test
    public void test_era_loop() {
        for (int year = FIRST_TESTED_YEAR; year < FIRST_UNTESTED_YEAR; year++) {
            JulianDate base = JulianChronology.INSTANCE.date(year, 1, 1);
            assertEquals(year, base.get(YEAR));

            JulianEra era = expectedEraFor(year);
            assertEquals(era, base.getEra());

            int yearOfEra = expectedYearOfEraFor(year);
            assertEquals(yearOfEra, base.get(YEAR_OF_ERA));

            JulianDate eraBased = JulianChronology.INSTANCE.date(era, yearOfEra, 1, 1);
            assertEquals(base, eraBased);
        }
    }

    private static JulianEra expectedEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD;
    }

    private static int expectedYearOfEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear;
    }
}
