package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_loop {

    private static final InternationalFixedChronology CHRONOLOGY = InternationalFixedChronology.INSTANCE;
    private static final InternationalFixedEra COMMON_ERA = InternationalFixedEra.CE;
    private static final int FIRST_SUPPORTED_YEAR = 1;
    private static final int EXCLUSIVE_UPPER_YEAR = 200;
    private static final int FIRST_MONTH = 1;
    private static final int FIRST_DAY_OF_MONTH = 1;

    @Test
    public void test_era_loop() {
        for (int year = FIRST_SUPPORTED_YEAR; year < EXCLUSIVE_UPPER_YEAR; year++) {
            assertFirstDayOfYearUsesCommonEra(year);
        }
    }

    private static void assertFirstDayOfYearUsesCommonEra(int year) {
        InternationalFixedDate base = CHRONOLOGY.date(year, FIRST_MONTH, FIRST_DAY_OF_MONTH);
        assertEquals(year, base.get(YEAR));
        assertEquals(COMMON_ERA, base.getEra());
        assertEquals(year, base.get(YEAR_OF_ERA));

        InternationalFixedDate eraBased = CHRONOLOGY.date(COMMON_ERA, year, FIRST_MONTH, FIRST_DAY_OF_MONTH);
        assertEquals(base, eraBased);
    }
}
