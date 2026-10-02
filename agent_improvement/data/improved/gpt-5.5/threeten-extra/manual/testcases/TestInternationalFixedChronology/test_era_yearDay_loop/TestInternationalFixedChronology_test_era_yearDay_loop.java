package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_era_yearDay_loop {

    private static final int FIRST_SUPPORTED_YEAR = 1;
    private static final int EXCLUSIVE_UPPER_YEAR = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;
    private static final InternationalFixedEra COMMON_ERA = InternationalFixedEra.CE;

    @Test
    public void test_era_yearDay_loop() {
        for (int year = FIRST_SUPPORTED_YEAR; year < EXCLUSIVE_UPPER_YEAR; year++) {
            assertFirstDayOfYearResolvesTheSameWithAndWithoutEra(year);
        }
    }

    private void assertFirstDayOfYearResolvesTheSameWithAndWithoutEra(int year) {
        InternationalFixedDate base = InternationalFixedChronology.INSTANCE.dateYearDay(year, FIRST_DAY_OF_YEAR);

        assertEquals(year, base.get(YEAR));
        assertEquals(COMMON_ERA, base.getEra());
        assertEquals(year, base.get(YEAR_OF_ERA));

        InternationalFixedDate eraBased = InternationalFixedChronology.INSTANCE.dateYearDay(
                COMMON_ERA,
                year,
                FIRST_DAY_OF_YEAR);
        assertEquals(base, eraBased);
    }
}
