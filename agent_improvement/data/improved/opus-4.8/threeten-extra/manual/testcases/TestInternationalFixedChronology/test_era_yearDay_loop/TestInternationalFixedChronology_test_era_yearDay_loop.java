package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_yearDay_loop {

    /**
     * For every year in the range [1, 200), building the first day of the year
     * via {@code dateYearDay(year, dayOfYear)} must agree with the era-based
     * {@code dateYearDay(era, yearOfEra, dayOfYear)} overload, and the resulting
     * date must report the expected year, era and year-of-era.
     */
    @Test
    public void test_era_yearDay_loop() {
        InternationalFixedEra era = InternationalFixedEra.CE;

        for (int year = 1; year < 200; year++) {
            InternationalFixedDate firstDayOfYear =
                    InternationalFixedChronology.INSTANCE.dateYearDay(year, 1);

            assertEquals(year, firstDayOfYear.get(YEAR));
            assertEquals(era, firstDayOfYear.getEra());
            assertEquals(year, firstDayOfYear.get(YEAR_OF_ERA));

            InternationalFixedDate eraBased =
                    InternationalFixedChronology.INSTANCE.dateYearDay(era, year, 1);
            assertEquals(firstDayOfYear, eraBased);
        }
    }
}
