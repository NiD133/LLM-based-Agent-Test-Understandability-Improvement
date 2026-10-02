package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_dateYearDay_badDate {

    @Test
    public void test_Chronology_dateYearDay_badDate() {
        // 2001 is not a leap year, so day-of-year 366 is out of range
        assertThrows(DateTimeException.class, () -> BritishCutoverChronology.INSTANCE.dateYearDay(2001, 366));
    }
}
