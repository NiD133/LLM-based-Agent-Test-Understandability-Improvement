package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_era_loop {

    private static final int FIRST_TESTED_YEAR = -200;
    private static final int LAST_TESTED_YEAR_EXCLUSIVE = 200;
    private static final int FIRST_MONTH = 1;
    private static final int FIRST_DAY_OF_MONTH = 1;

    @Test
    public void test_era_loop() {
        for (int year = FIRST_TESTED_YEAR; year < LAST_TESTED_YEAR_EXCLUSIVE; year++) {
            PaxDate base = PaxChronology.INSTANCE.date(year, FIRST_MONTH, FIRST_DAY_OF_MONTH);
            assertEquals(year, base.get(YEAR));

            PaxEra expectedEra = expectedEraFor(year);
            assertEquals(expectedEra, base.getEra());

            int yearOfEra = yearOfEraFor(year);
            assertEquals(yearOfEra, base.get(YEAR_OF_ERA));

            PaxDate eraBased = PaxChronology.INSTANCE.date(expectedEra, yearOfEra, FIRST_MONTH, FIRST_DAY_OF_MONTH);
            assertEquals(base, eraBased);
        }
    }

    private static PaxEra expectedEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? PaxEra.BCE : PaxEra.CE;
    }

    private static int yearOfEraFor(int prolepticYear) {
        return prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear;
    }
}
