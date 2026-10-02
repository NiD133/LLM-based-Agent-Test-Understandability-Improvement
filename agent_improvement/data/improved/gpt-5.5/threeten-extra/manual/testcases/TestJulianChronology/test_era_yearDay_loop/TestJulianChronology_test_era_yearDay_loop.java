package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_era_yearDay_loop {

    private static final int FIRST_TESTED_PROLEPTIC_YEAR = -200;
    private static final int FIRST_UNTESTED_PROLEPTIC_YEAR = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;

    @Test
    public void test_era_yearDay_loop() {
        for (int prolepticYear = FIRST_TESTED_PROLEPTIC_YEAR;
                prolepticYear < FIRST_UNTESTED_PROLEPTIC_YEAR;
                prolepticYear++) {

            JulianDate dateFromProlepticYear =
                    JulianChronology.INSTANCE.dateYearDay(prolepticYear, FIRST_DAY_OF_YEAR);

            assertEquals(prolepticYear, dateFromProlepticYear.get(YEAR));

            JulianEra expectedEra = expectedEraFor(prolepticYear);
            assertEquals(expectedEra, dateFromProlepticYear.getEra());

            int expectedYearOfEra = expectedYearOfEraFor(prolepticYear);
            assertEquals(expectedYearOfEra, dateFromProlepticYear.get(YEAR_OF_ERA));

            JulianDate dateFromEraAndYearOfEra =
                    JulianChronology.INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, FIRST_DAY_OF_YEAR);
            assertEquals(dateFromProlepticYear, dateFromEraAndYearOfEra);
        }
    }

    private static JulianEra expectedEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD;
    }

    private static int expectedYearOfEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear;
    }
}
