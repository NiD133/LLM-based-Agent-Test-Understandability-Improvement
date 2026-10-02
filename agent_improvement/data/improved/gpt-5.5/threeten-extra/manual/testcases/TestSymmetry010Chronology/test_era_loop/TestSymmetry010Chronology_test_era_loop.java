package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_era_loop {

    @Test
    public void test_era_loop() {
        for (int year = 1; year < 200; year++) {
            assertEraDateRoundTrip(year, IsoEra.CE, year);
        }
        for (int year = -200; year < 0; year++) {
            assertEraDateRoundTrip(year, IsoEra.BCE, 1 - year);
        }
    }

    private void assertEraDateRoundTrip(int year, IsoEra era, int expectedYearOfEra) {
        Symmetry010Date base = Symmetry010Chronology.INSTANCE.date(year, 1, 1);
        assertEquals(year, base.get(YEAR));
        assertEquals(era, base.getEra());
        assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));

        Symmetry010Date eraBased = Symmetry010Chronology.INSTANCE.date(era, year, 1, 1);
        assertEquals(base, eraBased);
    }
}
