package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_loop {

    /**
     * Walks the first 199 proleptic years and checks that a date built directly
     * from a year is identical to the same date built via the (always CE) era.
     */
    @Test
    public void test_era_loop() {
        for (int year = 1; year < 200; year++) {
            InternationalFixedDate dateFromYear =
                    InternationalFixedChronology.INSTANCE.date(year, 1, 1);

            // The proleptic year and the year-of-era are the same in the single CE era.
            assertEquals(year, dateFromYear.get(YEAR));
            assertEquals(InternationalFixedEra.CE, dateFromYear.getEra());
            assertEquals(year, dateFromYear.get(YEAR_OF_ERA));

            // Building the date through the era must yield an equal date.
            InternationalFixedDate dateFromEra =
                    InternationalFixedChronology.INSTANCE.date(InternationalFixedEra.CE, year, 1, 1);
            assertEquals(dateFromYear, dateFromEra);
        }
    }
}
