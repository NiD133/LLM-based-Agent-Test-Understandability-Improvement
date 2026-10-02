package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_era_loop {

    private static final int FIRST_TESTED_YEAR = -200;
    private static final int LAST_TESTED_YEAR_EXCLUSIVE = 200;

    @Test
    public void test_era_loop() {
        for (int year = FIRST_TESTED_YEAR; year < LAST_TESTED_YEAR_EXCLUSIVE; year++) {
            BritishCutoverDate base = BritishCutoverChronology.INSTANCE.date(year, 1, 1);

            assertEquals(year, base.get(YEAR));

            JulianEra era = expectedEraFor(year);
            assertEquals(era, base.getEra());

            int yearOfEra = expectedYearOfEraFor(year);
            assertEquals(yearOfEra, base.get(YEAR_OF_ERA));

            BritishCutoverDate eraBased = BritishCutoverChronology.INSTANCE.date(era, yearOfEra, 1, 1);
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
