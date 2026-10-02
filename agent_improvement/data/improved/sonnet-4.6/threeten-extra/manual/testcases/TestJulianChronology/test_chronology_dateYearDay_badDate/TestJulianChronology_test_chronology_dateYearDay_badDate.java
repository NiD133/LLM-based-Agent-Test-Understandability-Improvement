package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_chronology_dateYearDay_badDate {

    /**
     * Year 2001 is not a leap year in the Julian calendar (only years divisible by 4 are),
     * so day-of-year 366 is out of range and must throw DateTimeException.
     */
    @Test
    public void test_chronology_dateYearDay_badDate() {
        assertThrows(DateTimeException.class, () -> JulianChronology.INSTANCE.dateYearDay(2001, 366));
    }
}
