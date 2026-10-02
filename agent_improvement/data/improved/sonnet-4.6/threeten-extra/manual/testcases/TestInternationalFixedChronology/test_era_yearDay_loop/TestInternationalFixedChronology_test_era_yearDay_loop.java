package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_era_yearDay_loop {

    @Test
    public void test_era_yearDay_loop() {
        for (int year = 1; year < 200; year++) {
            // Create the first day of the given year using the proleptic-year form
            InternationalFixedDate base = InternationalFixedChronology.INSTANCE.dateYearDay(year, 1);
            verifyEraConsistency(year, base);
        }
    }

    /**
     * Verifies that a date created via dateYearDay(prolepticYear, dayOfYear) exposes
     * consistent year, era and year-of-era values, and that re-creating it through
     * the era-based dateYearDay overload produces an equal date.
     */
    private void verifyEraConsistency(int expectedYear, InternationalFixedDate base) {
        assertEquals(expectedYear, base.get(YEAR));

        InternationalFixedEra expectedEra = InternationalFixedEra.CE;
        assertEquals(expectedEra, base.getEra());
        assertEquals(expectedYear, base.get(YEAR_OF_ERA));

        // Both creation paths must yield identical dates
        InternationalFixedDate eraBased =
                InternationalFixedChronology.INSTANCE.dateYearDay(expectedEra, expectedYear, 1);
        assertEquals(base, eraBased);
    }
}
