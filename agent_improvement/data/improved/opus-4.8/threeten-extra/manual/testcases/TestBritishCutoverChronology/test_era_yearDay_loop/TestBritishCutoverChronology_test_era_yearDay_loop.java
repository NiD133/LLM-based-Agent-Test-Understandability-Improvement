package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_era_yearDay_loop {

    /**
     * For every proleptic year in a range that spans the BC/AD boundary, a date built
     * from the proleptic year must agree with the equivalent date built from its era and
     * year-of-era. Years &lt;= 0 are in the BC era; years &gt; 0 are in the AD era.
     */
    @Test
    public void test_era_yearDay_loop() {
        for (int prolepticYear = -200; prolepticYear < 200; prolepticYear++) {
            BritishCutoverDate firstDayOfYear =
                    BritishCutoverChronology.INSTANCE.dateYearDay(prolepticYear, 1);

            // The proleptic year is preserved verbatim.
            assertEquals(prolepticYear, firstDayOfYear.get(YEAR));

            // Non-positive proleptic years fall in the BC era, positive years in AD.
            JulianEra expectedEra = (prolepticYear <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(expectedEra, firstDayOfYear.getEra());

            // BC counts backwards from year 1, so proleptic 0 -> year-of-era 1, -1 -> 2, etc.
            int expectedYearOfEra = (prolepticYear <= 0 ? 1 - prolepticYear : prolepticYear);
            assertEquals(expectedYearOfEra, firstDayOfYear.get(YEAR_OF_ERA));

            // Building the same date via (era, year-of-era) must yield an equal date.
            BritishCutoverDate fromEraAndYearOfEra =
                    BritishCutoverChronology.INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, 1);
            assertEquals(firstDayOfYear, fromEraAndYearOfEra);
        }
    }
}
