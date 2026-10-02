package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_era_yearDay_loop {

    private static final int FIRST_YEAR = 1;
    private static final int EXCLUSIVE_END_YEAR = 200;
    private static final int FIRST_DAY_OF_YEAR = 1;
    private static final IsoEra CE = IsoEra.CE;

    @Test
    public void test_era_yearDay_loop() {
        for (int year = FIRST_YEAR; year < EXCLUSIVE_END_YEAR; year++) {
            Symmetry454Date base = Symmetry454Chronology.INSTANCE.dateYearDay(year, FIRST_DAY_OF_YEAR);

            assertEquals(year, base.get(YEAR));
            assertEquals(CE, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));

            Symmetry454Date eraBased = Symmetry454Chronology.INSTANCE.dateYearDay(CE, year, FIRST_DAY_OF_YEAR);
            assertEquals(base, eraBased);
        }
    }
}
