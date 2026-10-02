package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_era_yearDay_loop {

    private static final int FIRST_SUPPORTED_YEAR = 1;
    private static final int FIRST_DAY_OF_YEAR = 1;
    private static final int EXCLUSIVE_UPPER_YEAR = 200;
    private static final IsoEra CE = IsoEra.CE;

    @Test
    public void test_era_yearDay_loop() {
        for (int year = FIRST_SUPPORTED_YEAR; year < EXCLUSIVE_UPPER_YEAR; year++) {
            Symmetry010Date base = Symmetry010Chronology.INSTANCE.dateYearDay(year, FIRST_DAY_OF_YEAR);

            assertEquals(year, base.get(YEAR));
            assertEquals(CE, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));

            Symmetry010Date eraBased = Symmetry010Chronology.INSTANCE.dateYearDay(CE, year, FIRST_DAY_OF_YEAR);
            assertEquals(base, eraBased);
        }
    }
}
