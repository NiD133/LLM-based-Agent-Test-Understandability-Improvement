package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_era_loop {

    private static final int FIRST_CE_YEAR = 1;
    private static final int CE_YEAR_LIMIT = 200;
    private static final int FIRST_BCE_YEAR = -200;
    private static final int BCE_YEAR_LIMIT = 0;
    private static final int FIRST_MONTH = 1;
    private static final int FIRST_DAY_OF_MONTH = 1;

    @Test
    public void test_era_loop() {
        for (int year = FIRST_CE_YEAR; year < CE_YEAR_LIMIT; year++) {
            assertEraMapping(year, IsoEra.CE, year);
        }
        for (int year = FIRST_BCE_YEAR; year < BCE_YEAR_LIMIT; year++) {
            assertEraMapping(year, IsoEra.BCE, 1 - year);
        }
    }

    private static void assertEraMapping(int year, IsoEra expectedEra, int expectedYearOfEra) {
        Symmetry454Date base = Symmetry454Chronology.INSTANCE.date(year, FIRST_MONTH, FIRST_DAY_OF_MONTH);

        assertEquals(year, base.get(YEAR));
        assertEquals(expectedEra, base.getEra());
        assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

        Symmetry454Date eraBased = Symmetry454Chronology.INSTANCE.date(
                expectedEra,
                year,
                FIRST_MONTH,
                FIRST_DAY_OF_MONTH);
        assertEquals(base, eraBased);
    }
}
