package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_era_yearDay_loop {

    private static final int START_YEAR_INCLUSIVE = -200;
    private static final int END_YEAR_EXCLUSIVE = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;

    @Test
    public void test_era_yearDay_loop() {
        for (int year = START_YEAR_INCLUSIVE; year < END_YEAR_EXCLUSIVE; year++) {
            PaxDate prolepticDate = PaxChronology.INSTANCE.dateYearDay(year, FIRST_DAY_OF_YEAR);
            PaxEra expectedEra = (year <= 0 ? PaxEra.BCE : PaxEra.CE);
            int expectedYearOfEra = (year <= 0 ? 1 - year : year);

            assertEquals(year, prolepticDate.get(YEAR));
            assertEquals(expectedEra, prolepticDate.getEra());
            assertEquals(expectedYearOfEra, prolepticDate.get(YEAR_OF_ERA));

            PaxDate eraDate = PaxChronology.INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, FIRST_DAY_OF_YEAR);
            assertEquals(prolepticDate, eraDate);
        }
    }
}
