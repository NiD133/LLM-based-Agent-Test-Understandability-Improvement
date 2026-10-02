package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_era_yearDay_loop {

    /**
     * Verifies that for each proleptic year, constructing a PaxDate via
     * dateYearDay(year, 1) and via dateYearDay(era, yearOfEra, 1) yield the
     * same date, and that the YEAR, ERA, and YEAR_OF_ERA fields round-trip correctly.
     *
     * BCE years are those <= 0 (proleptic year 0 maps to BCE year 1,
     * proleptic year -1 maps to BCE year 2, etc.).
     */
    @Test
    public void test_era_yearDay_loop() {
        for (int year = -200; year < 200; year++) {
            PaxDate base = PaxChronology.INSTANCE.dateYearDay(year, 1);
            assertEquals(year, base.get(YEAR));

            PaxEra expectedEra = (year <= 0 ? PaxEra.BCE : PaxEra.CE);
            assertEquals(expectedEra, base.getEra());

            int expectedYearOfEra = (year <= 0 ? 1 - year : year);
            assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

            PaxDate eraBased = PaxChronology.INSTANCE.dateYearDay(expectedEra, expectedYearOfEra, 1);
            assertEquals(base, eraBased);
        }
    }
}
