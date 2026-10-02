package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_era_yearDay_loop {

    private static final int FIRST_TESTED_PROLEPTIC_YEAR = -200;
    private static final int LAST_TESTED_PROLEPTIC_YEAR_EXCLUSIVE = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;

    @Test
    public void test_era_yearDay_loop() {
        for (int prolepticYear = FIRST_TESTED_PROLEPTIC_YEAR;
                prolepticYear < LAST_TESTED_PROLEPTIC_YEAR_EXCLUSIVE;
                prolepticYear++) {
            BritishCutoverDate prolepticYearDate =
                    BritishCutoverChronology.INSTANCE.dateYearDay(prolepticYear, FIRST_DAY_OF_YEAR);

            JulianEra expectedEra = expectedEraFor(prolepticYear);
            int expectedYearOfEra = expectedYearOfEraFor(prolepticYear);
            BritishCutoverDate eraYearDate = BritishCutoverChronology.INSTANCE.dateYearDay(
                    expectedEra,
                    expectedYearOfEra,
                    FIRST_DAY_OF_YEAR);

            assertEquals(prolepticYear, prolepticYearDate.get(YEAR));
            assertEquals(expectedEra, prolepticYearDate.getEra());
            assertEquals(expectedYearOfEra, prolepticYearDate.get(YEAR_OF_ERA));
            assertEquals(prolepticYearDate, eraYearDate);
        }
    }

    private static JulianEra expectedEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD;
    }

    private static int expectedYearOfEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear;
    }
}
